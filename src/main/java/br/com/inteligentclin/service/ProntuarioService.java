package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.converters.ProntuarioModelMapperConverter;
import br.com.inteligentclin.dtos.prontuarioDTO.ProntuarioModelDTO;
import br.com.inteligentclin.dtos.prontuarioDTO.ProntuarioSummaryDTO;
import br.com.inteligentclin.entity.Dentista;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.entity.Prontuario;
import br.com.inteligentclin.repository.IDentistaRepository;
import br.com.inteligentclin.repository.IPacienteRepository;
import br.com.inteligentclin.repository.IProntuarioRepository;
import br.com.inteligentclin.service.exception.DadoExistenteException;
import br.com.inteligentclin.service.exception.ValidadeProntuarioException;
import br.com.inteligentclin.service.utils.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProntuarioService {

    @Autowired
    private IProntuarioRepository prontuarioRepository;

    @Autowired
    private ProntuarioModelMapperConverter prontuarioConverter;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IDentistaRepository dentistaRepository;

    @Autowired
    private UtilDate utilDate;

    public ProntuarioModelDTO salvar(ProntuarioModelDTO prontuarioDTO, Long idPaciente, Long idDentista) {
        Prontuario prontuario = prontuarioConverter.mapModelDTOToEntity(prontuarioDTO, Prontuario.class);

        Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(() -> new DadoExistenteException(
                "Paciente não encontrado."
        ));

        Dentista dentista = dentistaRepository.findById(idDentista).orElseThrow(() -> new DadoExistenteException(
                "Dentista não encontrado."));

        try {
            if (paciente.getProntuario() == null) {
                prontuario.setPaciente(paciente);
                prontuario.setDentista(dentista);
                paciente.setProntuario(prontuario);
            } else {
                Prontuario prontuarioDaBase = paciente.getProntuario();
                prontuarioDaBase.setDentista(dentista);
                prontuarioDaBase.setPlanoTratamento(prontuario.getPlanoTratamento());
                prontuarioDaBase.setEvolucaoTratamento(prontuario.getEvolucaoTratamento());
                prontuarioDaBase.setFile(prontuario.getFile());
            }
            Paciente pacienteSalvo = pacienteRepository.saveAndFlush(paciente);
            return prontuarioConverter.mapEntityToModelDTO(pacienteSalvo.getProntuario(), ProntuarioModelDTO.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<ProntuarioModelDTO> buscarPorId(Long id) {
        Prontuario prontuario = prontuarioRepository.findById(id).orElseThrow(() -> new DadoExistenteException(
                "Prontuário não encontrado."
        ));

        return Optional.ofNullable(prontuarioConverter.mapEntityToModelDTO(prontuario, ProntuarioModelDTO.class));
    }

    public Page<ProntuarioSummaryDTO> buscarTodos(Pageable pageable) {
        Page<Prontuario> pageProntuarios = prontuarioRepository.findAll(pageable);
        return pageProntuarios.map(prontuario ->
                prontuarioConverter.mapEntityToSummaryDTO(prontuario, ProntuarioSummaryDTO.class));
    }

    public void excluirPorId(Long id) throws ValidadeProntuarioException {
        Prontuario prontuario = prontuarioRepository.findById(id).orElseThrow(() -> new DadoExistenteException(
                "Prontuário não encontrado."
        ));

        LocalDate ultimaAtualizacao = prontuario.getUltimaAlteracao().toLocalDate();

        if (prontuario.getPaciente() == null && utilDate.verificarValidadeProntuario(ultimaAtualizacao)) {
            prontuarioRepository.deleteById(id);
        } else
            throw new ValidadeProntuarioException("Impossível excluir Prontuário. Com base na Resolução do CFO nº91/2009," +
                    " o tempo mínimo para a manutenção de prontuários odontológicos em suporte de papel são 10 (dez) anos," +
                    " desde a data do último registro e o prontuário informado ainda não atingiu esse prazo." +
                    " (Essa resolução estabelece também os critérios para a digitalização de documentos.)");
    }


}
