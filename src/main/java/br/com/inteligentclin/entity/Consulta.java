package br.com.inteligentclin.entity;

import br.com.inteligentclin.entity.enums.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "consulta_id")
    @GeneratedValue(generator = "consulta", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "paciente_id", foreignKey = @ForeignKey(name = "fk_paciente"))
    private Paciente paciente;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dentista_id", foreignKey = @ForeignKey(name = "fk_dentista"))
    private Dentista dentista;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario"))
    private Usuario usuario;

//    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataConsulta;

//    @Pattern(regexp="\\d{2}\\:\\d{2}", message = "Informe uma data no formato HH:mm")
    private LocalTime horaConsulta;

    private String complemento;

    private Double valor;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status;

}
