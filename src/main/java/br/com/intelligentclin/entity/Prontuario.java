package br.com.intelligentclin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "paciente_id", foreignKey = @ForeignKey(name = "fk_paciente_prontuario"))
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "dentista_id", foreignKey = @ForeignKey(name = "fk_dentista_prontuario"))
    private Dentista dentista;

    @Column(name = "plano_tratamento", length = 800)
    private String planoTratamento;

    @Column(name = "evolucao_tratamento", length = 800)
    private String evolucaoTratamento;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "ultima_alteracao")
    private LocalDateTime ultimaAlteracao;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "fk_file_prontuario"))
    private File file;

    @PreUpdate
    public void gerarUltimaAlteracao() {
        this.ultimaAlteracao = LocalDateTime.now();
    }

}