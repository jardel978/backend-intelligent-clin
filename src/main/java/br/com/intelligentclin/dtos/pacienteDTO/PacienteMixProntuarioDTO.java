package br.com.intelligentclin.dtos.pacienteDTO;

import br.com.intelligentclin.dtos.PessoaSummaryDTO;
import br.com.intelligentclin.entity.Idade;
import br.com.intelligentclin.entity.enums.Sexo;
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
