package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.converters.PacienteModelMapperConverter;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import br.com.inteligentclin.entity.Idade;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.repository.IEnderecoRepository;
import br.com.inteligentclin.repository.IPacienteRepository;
import br.com.inteligentclin.repository.PessoaCustomRepository;
import br.com.inteligentclin.service.exception.DadoExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IEnderecoRepository enderecoRepository;

    @Autowired
    private PacienteModelMapperConverter pacienteConverter;

    @Autowired
    private PessoaCustomRepository<Paciente> pacienteModelCustomRepository;

    public PacienteModelDTO salvar(PacienteModelDTO pacienteDTO) {
        Paciente paciente = pacienteConverter.mapModelDTOToEntity(pacienteDTO, Paciente.class);

        pacienteRepository.save(paciente);

        paciente.setIdade(gerarIdade(paciente.getDataNascimento(), LocalDate.now()));
        return pacienteConverter.mapEntityToModelDTO(paciente, PacienteModelDTO.class);
    }

    public Optional<PacienteModelDTO> buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new DadoExistenteException("Paciente não encontrado"));

        Optional<PacienteModelDTO> pacienteDTO = Optional.ofNullable(pacienteConverter.mapEntityToModelDTO(paciente,
                PacienteModelDTO.class));

        pacienteDTO.get().setIdade(gerarIdade(paciente.getDataNascimento(), LocalDate.now()));

        return pacienteDTO;
    }

    public Page<PacienteModelDTO> buscarCustomizado(Pageable pageable,
                                                    Long id,
                                                    String nome,
                                                    String sobrenome,
                                                    String cpf) {
        List<Paciente> lista = pacienteModelCustomRepository.find(id, nome, sobrenome, cpf, Paciente.class);

        List<PacienteModelDTO> listaDTO = new ArrayList<>();

        lista.stream().forEach(paciente -> {
            paciente.setIdade(gerarIdade(paciente.getDataNascimento(), LocalDate.now()));
            PacienteModelDTO pacienteModelDTO = pacienteConverter.mapEntityToModelDTO(paciente,
                    PacienteModelDTO.class);
            listaDTO.add(pacienteModelDTO);
        });

        return new PageImpl<>(listaDTO, pageable, listaDTO.stream().count());
    }

    public Page<PacienteSummaryDTO> buscarTodos(Pageable pageable) {
        List<Paciente> lista = pacienteRepository.findAll();
        List<PacienteSummaryDTO> listaSummaryDTO = new ArrayList<>();

        lista.stream().forEach(paciente -> {
            paciente.setIdade(gerarIdade(paciente.getDataNascimento(), LocalDate.now()));
            PacienteSummaryDTO pacienteSummaryDTO = pacienteConverter.mapEntityToSummaryDTO(paciente,
                    PacienteSummaryDTO.class);
            listaSummaryDTO.add(pacienteSummaryDTO);
        });

        return new PageImpl<>(listaSummaryDTO, pageable, listaSummaryDTO.stream().count());
    }

    public void excluirPorId(Long id) {
        try {
            pacienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um paciente que está vinculado a uma " +
                    "consulta.");
        }
    }


    public void atualizar(Long id, PacienteModelDTO pacienteDTO) {

        PacienteModelDTO pacienteDaBase = buscarPorId(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));

        pacienteDaBase.setNome(pacienteDTO.getNome());
        pacienteDaBase.setSobrenome(pacienteDTO.getSobrenome());
        pacienteDaBase.setDataCadastro(pacienteDTO.getDataCadastro());
        pacienteDaBase.setCpf(pacienteDTO.getCpf());
        pacienteDaBase.setEmail(pacienteDTO.getEmail());
        pacienteDaBase.setTelefone(pacienteDTO.getTelefone());
        pacienteDaBase.setDataNascimento(pacienteDTO.getDataNascimento());
        pacienteDaBase.setEndereco(pacienteDTO.getEndereco());
        pacienteDaBase.setConsultas(pacienteDTO.getConsultas());
        pacienteDaBase.setProntuario(pacienteDTO.getProntuario());
        pacienteDaBase.setSexo(pacienteDTO.getSexo());
        salvar(pacienteDaBase);
    }

    public static Idade gerarIdade(LocalDate dataInicial, LocalDate dataFinal) {

        Period period = Period.between(dataInicial, dataFinal);

        return Idade.builder()
                .dias(period.getDays())
                .meses(period.getMonths())
                .anos(period.getYears()).build();
    }
}

