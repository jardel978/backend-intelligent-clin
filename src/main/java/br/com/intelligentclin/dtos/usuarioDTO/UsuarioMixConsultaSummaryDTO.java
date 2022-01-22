package br.com.intelligentclin.dtos.usuarioDTO;

import br.com.intelligentclin.dtos.PessoaMixConsultaSummaryDTO;
import br.com.intelligentclin.entity.enums.Cargo;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioMixConsultaSummaryDTO extends PessoaMixConsultaSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cargo cargo;

}