package br.com.intelligentclin.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaModelDTO {

    private Long id;

    @NotNull(message = "O nome deve ser preenchido.")
    private String nome;

    @NotNull(message = "Por gentileza, informe o sobrenome.")
    private String sobrenome;

    //    @UniqueConstraint(message = "Esse número de CPF já está vinculado a alguém! Informe um CPF único para cada indivíduo.")
    @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)", message = "Informe um CPF no formato: 000.000.000-00")
    @NotNull(message = "O CPF é obrigatório.")
    private String cpf;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

    @NotNull(message = "Por gentileza, informe um endereço de email.")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message =
            "Formato de email inválido. Exemplo: meuemail@gmail.com")
    private String email;

    @Pattern(regexp = "^\\(?(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", message =
            "Formato de número de telefone inválido. Tente: xx xxxxx-xxxx e verifique se o DDD está correto.")
    @NotNull(message = "É necessário informar um número de telefone para contato.")
    private String telefone;
}
