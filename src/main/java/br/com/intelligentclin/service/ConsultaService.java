package br.com.intelligentclin.service;

import br.com.intelligentclin.dtos.consultaDTO.ConsultaModelDTO;
import br.com.intelligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.intelligentclin.dtos.converters.ConsultaModelMapperConverter;
import br.com.intelligentclin.entity.Consulta;
import br.com.intelligentclin.entity.Dentista;
import br.com.intelligentclin.entity.Paciente;
import br.com.intelligentclin.entity.Usuario;
import br.com.intelligentclin.entity.enums.StatusConsulta;
import br.com.intelligentclin.repository.IConsultaRepository;
import br.com.intelligentclin.repository.IDentistaRepository;
import br.com.intelligentclin.repository.IPacienteRepository;
import br.com.intelligentclin.repository.IUsuarioRepository;
import br.com.intelligentclin.service.exception.ConsultaStatusException;
import br.com.intelligentclin.service.exception.DadoInexistenteException;
import br.com.intelligentclin.service.utils.UtilDate;
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
        LocalDate dataConsultaDTO = consultaDTO.getDataConsulta();
        LocalTime horaConsultaDTO = consultaDTO.getHoraConsulta();
        Consulta consultaMarcada = consultaRepository.findByDataConsultaAndHoraConsulta(dataConsultaDTO,
                horaConsultaDTO);

        LocalDateTime dataTime = LocalDateTime.of(
                dataConsultaDTO.getYear(),
                dataConsultaDTO.getMonth(),
                dataConsultaDTO.getDayOfMonth(),
                horaConsultaDTO.getHour(),
                horaConsultaDTO.getMinute(),
                horaConsultaDTO.getSecond()
        );

        if (consultaMarcada == null
                || consultaMarcada.getStatus() == StatusConsulta.CANCELADA) {
            if (Boolean.FALSE.equals(utilDate.verificarSeDataAnterior(dataTime))) {

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
                throw new ConsultaStatusException("A data/hora escolhida para agendamento da consulta é inferior a " +
                        "data " +
                        "de hoje. Por favor, informe uma data posterior a data atual.");
        } else {
            if (consultaMarcada.getStatus() == StatusConsulta.PENDENTE)
                throw new ConsultaStatusException("Já possui uma consulta agendada para a data: " +
                        dataConsultaDTO + " e horário: " + horaConsultaDTO + ". Porém, ainda não foi confirmada pelo " +
                        "paciente " +
                        consultaMarcada.getPaciente().getNome() + " " + consultaMarcada.getPaciente().getSobrenome() +
                        ". Verifique a confirmação do paciente para saber se o horário estará ou não disponível. " +
                        "Telefone para contato: " + consultaMarcada.getPaciente().getTelefone());
            if (consultaMarcada.getStatus() == StatusConsulta.CONFIRMADA)
                throw new ConsultaStatusException("Já possui uma consulta agendada e confirmada para a data: " +
                        dataConsultaDTO + " e horário: " + horaConsultaDTO + " com o paciente: " +
                        consultaMarcada.getPaciente().getNome() + " " + consultaMarcada.getPaciente().getSobrenome() + ".");
            if (consultaMarcada.getStatus() == StatusConsulta.REALIZADA)
                throw new ConsultaStatusException("Já possui uma consulta agendada e confirmada para a data: " +
                        dataConsultaDTO + " e horário: " + horaConsultaDTO + " com o paciente: " +
                        consultaMarcada.getPaciente().getNome() + " " + consultaMarcada.getPaciente().getSobrenome() + ".");
            return null;
        }
    }

    public ConsultaModelDTO buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new DadoInexistenteException(
                "A consulta informada não pode ser localizada na base de dados."));
        consulta.setStatus(veirificarSeConsultaRealizada(consulta));
        return consultaConverter.mapEntityToModelDTO(consulta, ConsultaModelDTO.class);
    }

    public Page<ConsultaSummaryDTO> buscarTodos(Pageable pageable) {
        Page<Consulta> pageConsultas = consultaRepository.findAll(pageable);
        return pageConsultas.map(consulta -> {
            consulta.setStatus(veirificarSeConsultaRealizada(consulta));
            return consultaConverter.mapEntityToSummaryDTO(consulta,
                    ConsultaSummaryDTO.class);
        });
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

        LocalDate dataConsultaDTO = consultaDTO.getDataConsulta();
        LocalTime horaConsultaDTO = consultaDTO.getHoraConsulta();
        Consulta consultaMarcada = consultaRepository.findByDataConsultaAndHoraConsulta(dataConsultaDTO,
                horaConsultaDTO);

        LocalDateTime dataTime = LocalDateTime.of(
                dataConsultaDTO.getYear(),
                dataConsultaDTO.getMonth(),
                dataConsultaDTO.getDayOfMonth(),
                horaConsultaDTO.getHour(),
                horaConsultaDTO.getMinute(),
                horaConsultaDTO.getSecond()
        );

        boolean verificarDataConsulta =
                Boolean.FALSE.equals(utilDate.verificarSeDataAnterior(dataTime));

        boolean podeAtualizarConsulta = true;
        //se tem consulta marcada -> se for a mesma a ser atualizada ou status cancelada: atualizarConsulta = true
        if (consultaMarcada != null) {
            podeAtualizarConsulta = consultaDaBase.getId() == consultaMarcada.getId()
                    || consultaMarcada.getStatus() == StatusConsulta.CANCELADA;
        }

        if (verificarDataConsulta) {
            if (podeAtualizarConsulta) {
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
                throw new ConsultaStatusException("Já possui uma consulta agendada e confirmada para a data: " +
                        dataConsultaDTO + " e horário: " + horaConsultaDTO + " com o paciente: " +
                        consultaMarcada.getPaciente().getNome() + " " + consultaMarcada.getPaciente().getSobrenome() + ".");
        } else
            throw new ConsultaStatusException("A data/hora escolhida para agendamento da consulta é inferior a data " +
                    "de hoje. Por favor, informe uma data posterior a data atual.");
    }

    public StatusConsulta veirificarSeConsultaRealizada(Consulta consulta) {
        //se consulta confirmada e a sua data já passou -> retornar status de realizada. Se não, retornar status recebido
        LocalDateTime dataTime = LocalDateTime.of(
                consulta.getDataConsulta().getYear(),
                consulta.getDataConsulta().getMonth(),
                consulta.getDataConsulta().getDayOfMonth(),
                consulta.getHoraConsulta().getHour(),
                consulta.getHoraConsulta().getMinute(),
                consulta.getHoraConsulta().getSecond()
        );
        if (consulta.getStatus() == StatusConsulta.CONFIRMADA
                && Boolean.TRUE.equals(utilDate.verificarSeDataAnterior(dataTime)))
            return StatusConsulta.REALIZADA;
        else
            return consulta.getStatus();
    }
}