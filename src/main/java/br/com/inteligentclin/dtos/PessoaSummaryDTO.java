package br.com.inteligentclin.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaSummaryDTO {

    private Long id;

    @NotNull(message = "Por gentileza, informe o sobrenome.")
    private String nome;

    @NotNull(message = "O nome deve ser preenchido.")
    private String sobrenome;
}
