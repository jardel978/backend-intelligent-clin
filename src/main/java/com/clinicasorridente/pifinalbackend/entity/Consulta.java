package com.clinicasorridente.pifinalbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_CONSULTA")
@SequenceGenerator(name = "consulta", sequenceName = "SQ_TB_CONSULTA", allocationSize = 1)
public class Consulta implements Serializable {

    @Id
    @Column(name = "consulta_id")
    @GeneratedValue(generator = "consulta", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @NotNull(message = "Por gentileza, informe o paciente para essa consulta.")
    @JoinColumn(name = "paciente_id", foreignKey = @ForeignKey(name = "fk_paciente"))
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @NotNull(message = "É necessário informar qual o dentista fará o atendimento.")
    @JoinColumn(name = "dentista_id", foreignKey = @ForeignKey(name = "fk_dentista"))
    private Dentista dentista;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @NotNull(message = "Não é possível registrar uma consulta sem informar o usuário que a está registrando.")
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario"))
    private Usuario usuario;

    @Column(name = "consulta_data")
    @NotNull(message = "Informe a data da consulta.")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dataConsulta;

    @Column(name = "consulta_horario")
    @NotNull(message = "Informe o horário da consulta.")
    @Pattern(regexp="\\d{2}\\:\\d{2}", message = "Informe uma data no formato HH:mm")
    private String horaConsulta;

}
