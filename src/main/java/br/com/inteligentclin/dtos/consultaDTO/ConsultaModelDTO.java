package br.com.inteligentclin.dtos.consultaDTO;

import br.com.inteligentclin.entity.Dentista;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.entity.Usuario;
import br.com.inteligentclin.entity.enums.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaModelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "Por gentileza, informe o paciente para essa consulta.")
    private Paciente paciente;

    @NotNull(message = "É necessário informar qual o dentista fará o atendimento.")
    private Dentista dentista;

    @NotNull(message = "Não é possível registrar uma consulta sem informar o usuário que a está registrando.")
    private Usuario usuario;

    @NotNull(message = "Informe a data da consulta.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataConsulta;

    @NotNull(message = "Informe o horário da consulta.")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaConsulta;

    private String complemento;

    @Digits(fraction = 2, integer = 3, message = "Informe um valor monetário válido. Exemplo: R$ 100,00")
    private Double valor;

    private StatusConsulta status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacaoStatus;

}
