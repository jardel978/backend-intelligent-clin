package br.com.inteligentclin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PRONTUARIO")
@SequenceGenerator(name = "prontuario", sequenceName = "SQ_TB_PRONTUARIO", allocationSize = 1)
public class Prontuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "prontuario_id")
    @GeneratedValue(generator = "prontuario", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", foreignKey = @ForeignKey(name = "fk_paciente_prontuario"))
    private Paciente paciente;

//    private byte radiografias;

    @ManyToOne
    @JoinColumn(name = "dentista_id", foreignKey = @ForeignKey(name = "fk_dentista_prontuario"))
    private Dentista dentista;

    @Column(name = "plano_tratamento")
    private String planoTratamento;

    @Column(name = "evolucao_tratamento")
    private String evolucaoTratamento;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "fk_file_prontuario"))
    private File file;

}