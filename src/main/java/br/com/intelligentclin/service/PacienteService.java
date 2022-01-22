package br.com.intelligentclin.service;

import br.com.intelligentclin.dtos.converters.PacienteModelMapperConverter;
import br.com.intelligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.intelligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import br.com.intelligentclin.entity.Endereco;
import br.com.intelligentclin.entity.Paciente;
import br.com.intelligentclin.repository.IEnderecoRepository;
import br.com.intelligentclin.repository.IPacienteRepository;
import br.com.intelligentclin.repository.PessoaCustomRepository;
import br.com.intelligentclin.service.exception.DadoInexistenteException;
import br.com.intelligentclin.service.exception.EntidadeRelacionadaException;
import br.com.intelligentclin.service.utils.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
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
        Paciente pacienteSalvo = pacienteRepository.saveAndFlush(paciente);
        pacienteSalvo.setIdade(utilDate.gerarIdade(pacienteSalvo.getDataNascimento(), LocalDate.now()));
        return pacienteConverter.mapEntityToModelDTO(pacienteSalvo, PacienteModelDTO.class);
    }

    public Optional<PacienteModelDTO> buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new DadoInexistenteException("Paciente não encontrado"));

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
            PacienteSummaryDTO pacienteDTO = pacienteConverter.mapEntityToSummaryDTO(paciente,
                    PacienteSummaryDTO.class);
            pacienteDTO.setTemConsultas(verificarSePacienteTemConsultas(paciente));
            return pacienteDTO;
        });
    }

    public void excluirPorId(Long id) throws EntidadeRelacionadaException {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() ->
                new DadoInexistenteException("O Paciente informado não pode ser localizado na base de dados."));
        boolean temProntuario = paciente.getProntuario() != null;
        boolean temConsulta = !paciente.getConsultas().isEmpty();
        String baseMensagemException = "Por questões de segurança de dados, não é possível excluir o(a) " +
                "paciente: " + paciente.getNome() + " " + paciente.getSobrenome() + " pois está vinculado(a) a ";
        if (temProntuario && temConsulta)
            throw new EntidadeRelacionadaException(baseMensagemException + "um prontuário e a uma ou mais consultas.");
        if (temProntuario)
            throw new EntidadeRelacionadaException(baseMensagemException + "um prontuário.");
        if (temConsulta)
            throw new EntidadeRelacionadaException(baseMensagemException + "uma ou mais consultas.");
//      verificar se o endereço existe,
        Endereco enderecoExite = verificarEnderecoExistente(paciente.getEndereco());
//                        se ele não tem associação com mais de um paciente e só assim excluí-lo
        if (enderecoExite != null && enderecoExite.getPacientes().size() == 1)
            enderecoRepository.delete(enderecoExite);

        pacienteRepository.deleteById(paciente.getId());
    }


    public void atualizar(Long id, PacienteModelDTO pacienteDTO) {

        PacienteModelDTO pacienteDaBase = buscarPorId(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));

        pacienteDaBase.setNome(pacienteDTO.getNome());
        pacienteDaBase.setSobrenome(pacienteDTO.getSobrenome());
        pacienteDaBase.setCpf(pacienteDTO.getCpf());
        pacienteDaBase.setEmail(pacienteDTO.getEmail());
        pacienteDaBase.setTelefone(pacienteDTO.getTelefone());
        pacienteDaBase.setDataNascimento(pacienteDTO.getDataNascimento());
        pacienteDaBase.setEndereco(pacienteDTO.getEndereco());
        pacienteDaBase.setConsultas(pacienteDTO.getConsultas());
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

    public Boolean verificarSePacienteTemConsultas(Paciente paciente) {
        return !paciente.getConsultas().isEmpty();
    }
}