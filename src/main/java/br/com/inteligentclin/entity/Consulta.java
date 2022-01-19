package br.com.inteligentclin.entity;

import br.com.inteligentclin.entity.enums.StatusConsulta;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @ManyToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "paciente_id", foreignKey = @ForeignKey(name = "fk_paciente"))
    private Paciente paciente;

    @ManyToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dentista_id", foreignKey = @ForeignKey(name = "fk_dentista"))
    private Dentista dentista;

    @ManyToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario"))
    private Usuario usuario;

    private LocalDate dataConsulta;

    private LocalTime horaConsulta;

    private String complemento;

    private Double valor;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status;

    private LocalDateTime dataAtualizacaoStatus;

}
