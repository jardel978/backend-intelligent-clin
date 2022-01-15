package br.com.inteligentclin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_FILE")
@SequenceGenerator(name = "file", sequenceName = "SQ_TB_FILE", allocationSize = 1)
public class File {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(generator = "file", strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    private String nome;

    private byte[] data;

    private String tipo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "prontuario_id", foreignKey = @ForeignKey(name = "fk_prontuario_file"))
    private Prontuario prontuario;

    @PrePersist
    protected void dataDeCriacao() {
        this.dataCriacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void dataDeAtualizacao() {
        this.dataAtualizacao = LocalDateTime.now();
    }

}
