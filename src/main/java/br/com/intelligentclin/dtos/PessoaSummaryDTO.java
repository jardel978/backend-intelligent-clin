package br.com.intelligentclin.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaSummaryDTO {

    private Long id;

    private String nome;

    private String sobrenome;
}
