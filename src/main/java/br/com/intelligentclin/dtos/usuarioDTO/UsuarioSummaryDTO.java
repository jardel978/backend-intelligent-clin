package br.com.intelligentclin.dtos.usuarioDTO;

import br.com.intelligentclin.dtos.PessoaSummaryDTO;
import br.com.intelligentclin.entity.enums.Cargo;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSummaryDTO extends PessoaSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String login;

    private Cargo cargo;

}