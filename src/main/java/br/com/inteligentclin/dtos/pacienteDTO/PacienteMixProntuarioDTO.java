package br.com.inteligentclin.dtos.pacienteDTO;

import br.com.inteligentclin.dtos.PessoaSummaryDTO;
import br.com.inteligentclin.entity.Idade;
import br.com.inteligentclin.entity.enums.Sexo;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteMixProntuarioDTO extends PessoaSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Idade idade;
    private Sexo sexo;

}
