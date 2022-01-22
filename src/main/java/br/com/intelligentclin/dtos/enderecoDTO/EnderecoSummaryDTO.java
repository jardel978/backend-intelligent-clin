package br.com.intelligentclin.dtos.enderecoDTO;

import lombok.*;

import javax.validation.constraints.Digits;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String rua;
    private String numero;
    private String bairro;

    @Digits(fraction = 0, integer = 8, message = "CEP inválido. Certifique-se de ter informado apenas 8 caracteres " +
            "numéricos.")
    private String cep;

    private String cidade;
    private String estado;

}
