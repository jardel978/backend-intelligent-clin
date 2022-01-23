package br.com.intelligentclin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;

    private String sobrenome;

    @Column(unique = true)
    private String cpf;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @Column(unique = true)
    private String email;

    private String telefone;
}
