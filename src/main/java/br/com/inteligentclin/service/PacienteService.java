package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.converters.PacienteModelMapperConverter;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import br.com.inteligentclin.entity.Endereco;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.repository.IEnderecoRepository;
import br.com.inteligentclin.repository.IPacienteRepository;
import br.com.inteligentclin.repository.PessoaCustomRepository;
import br.com.inteligentclin.service.exception.DadoExistenteException;
import br.com.inteligentclin.service.utils.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    @Autowired
    private UtilDate utilDate;

    public PacienteModelDTO salvar(PacienteModelDTO pacienteDTO) {
        Paciente paciente = pacienteConverter.mapModelDTOToEntity(pacienteDTO, Paciente.class);

        Endereco enderecoExite = verificarEnderecoExistente(pacienteDTO.getEndereco());
        if (enderecoExite != null)
            paciente.setEndereco(enderecoExite);

        enderecoRepository.save(paciente.getEndereco());
        pacienteRepository.save(paciente);

        paciente.setIdade(utilDate.gerarIdade(paciente.getDataNascimento(), LocalDate.now()));
        return pacienteConverter.mapEntityToModelDTO(paciente, PacienteModelDTO.class);
    }

    public Optional<PacienteModelDTO> buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new DadoExistenteException("Paciente não encontrado"));

        paciente.setIdade(utilDate.gerarIdade(paciente.getDataNascimento(), LocalDate.now()));
        return Optional.ofNullable(pacienteConverter.mapEntityToModelDTO(paciente,
                PacienteModelDTO.class));
    }

    public Page<PacienteModelDTO> buscarCustomizado(Pageable pageable,
                                                    Long id,
                                                    String nome,
                                                    String sobrenome,
                                                    String cpf) {
        List<Paciente> lista = pacienteModelCustomRepository.find(id, nome, sobrenome, cpf, Paciente.class);
        Page<Paciente> pagePacientes = new PageImpl<>(lista, pageable, lista.stream().count());

        return pagePacientes.map(paciente -> {
            paciente.setIdade(utilDate.gerarIdade(paciente.getDataNascimento(), LocalDate.now()));
            return pacienteConverter.mapEntityToModelDTO(paciente, PacienteModelDTO.class);
        });
    }

    public Page<PacienteSummaryDTO> buscarTodos(Pageable pageable) {
        Page<Paciente> lista = pacienteRepository.findAll(pageable);

        return lista.map(paciente -> {
            paciente.setIdade(utilDate.gerarIdade(paciente.getDataNascimento(), LocalDate.now()));
            return pacienteConverter.mapEntityToSummaryDTO(paciente, PacienteSummaryDTO.class);
        });
    }

    public void excluirPorId(Long id) {
        try {
            buscarPorId(id)
                    .map(paciente -> {
//                        verificar se o endereço existe,
                        Endereco enderecoExite = verificarEnderecoExistente(paciente.getEndereco());
//                        se ele não tem associação com mais de um paciente e só assim excluí-lo
                        if (enderecoExite != null && enderecoExite.getPacientes().size() == 1) {
                            enderecoRepository.delete(enderecoExite);
                        }

                        pacienteRepository.deleteById(paciente.getId());
                        return Void.TYPE;
                    }).orElseThrow(() -> new DadoExistenteException(
                            "Paciente não encontrado")
                    );

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
//        pacienteDaBase.setProntuario(pacienteDTO.getProntuario());
        pacienteDaBase.setSexo(pacienteDTO.getSexo());
        salvar(pacienteDaBase);
    }

    public Endereco verificarEnderecoExistente(Endereco endereco) {
        return enderecoRepository.findByRuaAndNumeroAndBairro(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro()
        );
    }
}