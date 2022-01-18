package br.com.inteligentclin.dtos.usuarioDTO;

import br.com.inteligentclin.dtos.PessoaSummaryDTO;
import br.com.inteligentclin.entity.enums.Cargo;
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