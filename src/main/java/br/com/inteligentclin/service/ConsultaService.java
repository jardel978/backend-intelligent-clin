package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.consultaDTO.ConsultaModelDTO;
import br.com.inteligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.inteligentclin.dtos.converters.ConsultaModelMapperConverter;
import br.com.inteligentclin.entity.Consulta;
import br.com.inteligentclin.entity.Dentista;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.entity.Usuario;
import br.com.inteligentclin.entity.enums.StatusConsulta;
import br.com.inteligentclin.repository.IConsultaRepository;
import br.com.inteligentclin.repository.IDentistaRepository;
import br.com.inteligentclin.repository.IPacienteRepository;
import br.com.inteligentclin.repository.IUsuarioRepository;
import br.com.inteligentclin.service.exception.ConsultaStatusException;
import br.com.inteligentclin.service.exception.DadoInexistenteException;
import br.com.inteligentclin.service.utils.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ConsultaService {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IDentistaRepository dentistaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ConsultaModelMapperConverter consultaConverter;

    @Autowired
    private UtilDate utilDate;

    public ConsultaModelDTO salvar(ConsultaModelDTO consultaDTO,
                                   Long idPaciente, Long idDentista, Long idUsuario) {
        LocalDate dataConsulta = consultaDTO.getDataConsulta();
        LocalTime horaConsulta = consultaDTO.getHoraConsulta();
        Consulta consultaMarcada = consultaRepository.findByDataConsultaAndHoraConsulta(dataConsulta, horaConsulta);

        if (consultaMarcada == null
                || consultaMarcada.getStatus() == StatusConsulta.CANCELADA) {
            if (!utilDate.verificarSeDataAnterior(dataConsulta)) {

                String mensagemException = "não encontrado na base de dados.";
                Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(() -> new DadoInexistenteException(
                        "Paciente " + mensagemException));
                Dentista dentista = dentistaRepository.findById(idDentista).orElseThrow(() -> new DadoInexistenteException(
                        "Dentista " + mensagemException));
                Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new DadoInexistenteException(
                        "Usuário " + mensagemException));

                consultaDTO.setPaciente(paciente);
                consultaDTO.setDentista(dentista);
                consultaDTO.setUsuario(usuario);
                Consulta consulta = consultaConverter.mapModelDTOToEntity(consultaDTO, Consulta.class);
                Consulta consultaSalva = consultaRepository.saveAndFlush(consulta);
                return consultaConverter.mapEntityToModelDTO(consultaSalva, ConsultaModelDTO.class);
            } else
                throw new ConsultaStatusException("A data escolhida para agendamento da consulta é inferior a data " +
                        "de hoje. Por favor, informe uma data posterior a data atual.");
        } else {
            if (consultaMarcada.getStatus() == StatusConsulta.PENDENTE)
                throw new ConsultaStatusException("Já possui uma consulta agendada para a data: " +
                        dataConsulta + " e horário: " + horaConsulta + ". Porém, ainda não foi confirmada pelo paciente " +
                        consultaMarcada.getPaciente().getNome() + " " + consultaMarcada.getPaciente().getSobrenome() +
                        ". Verifique a confirmação do paciente para saber se o horário estará ou não disponível. " +
                        "Telefone para contato: " + consultaMarcada.getPaciente().getTelefone());
            return null;
        }
    }

    public ConsultaModelDTO buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new DadoInexistenteException(
                "A consulta informada não pode ser localizada na base de dados."));
        return consultaConverter.mapEntityToModelDTO(consulta, ConsultaModelDTO.class);
    }

    public Page<ConsultaSummaryDTO> buscarTodos(Pageable pageable) {
        Page<Consulta> pageConsultas = consultaRepository.findAll(pageable);
        return pageConsultas.map(consulta -> consultaConverter.mapEntityToSummaryDTO(consulta,
                ConsultaSummaryDTO.class));
    }

    public void excluirPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new DadoInexistenteException(
                "A consulta informada não pode ser encontrada na base de dados."));

        consultaRepository.deleteById(consulta.getId());
    }

    public void atualizar(Long idConsulta, ConsultaModelDTO consultaDTO,
                          Long idPaciente, Long idDentista, Long idUsuario) {
        Consulta consultaDaBase = consultaRepository.findById(idConsulta).orElseThrow(() -> new DadoInexistenteException(
                "A consulta informada não pode ser localizada na base de dados.")
        );

        if (!utilDate.verificarSeDataAnterior(consultaDTO.getDataConsulta())) {
            String mensagemException = "não encontrado na base de dados.";
            Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(() -> new DadoInexistenteException(
                    "Paciente " + mensagemException));
            Dentista dentista = dentistaRepository.findById(idDentista).orElseThrow(() -> new DadoInexistenteException(
                    "Dentista " + mensagemException));
            Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new DadoInexistenteException(
                    "Usuário " + mensagemException));

            consultaDaBase.setPaciente(paciente);
            consultaDaBase.setDentista(dentista);
            consultaDaBase.setUsuario(usuario);
            consultaDaBase.setDataConsulta(consultaDTO.getDataConsulta());
            consultaDaBase.setHoraConsulta(consultaDTO.getHoraConsulta());
            consultaDaBase.setComplemento(consultaDTO.getComplemento());
            consultaDaBase.setValor(consultaDTO.getValor());
            if (consultaDaBase.getStatus() != consultaDTO.getStatus()) {
                consultaDaBase.setStatus(consultaDTO.getStatus());
                consultaDaBase.setDataAtualizacaoStatus(LocalDateTime.now());
            }
            consultaRepository.save(consultaDaBase);
        } else
            throw new ConsultaStatusException("A data escolhida para agendamento da consulta é inferior a data " +
                    "de hoje. Por favor, informe uma data posterior a data atual.");
    }
}
