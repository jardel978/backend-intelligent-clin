package br.com.inteligentclin.dtos.usuarioDTO;

import br.com.inteligentclin.dtos.PessoaModelDTO;
import br.com.inteligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.inteligentclin.entity.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModelDTO extends PessoaModelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "É necessário informar um login para o novo usuário.")
    @Size(min = 8, max = 15, message = "Seu endereço de login deve ter no mínimo 8 dígitos.")
    private String login;

    @Size(min = 6, max = 15, message = "Sua senha deve ter no mínimo 6 dígitos.")
    private String senha;

    //    @JsonManagedReference
    @JsonIgnore
    private Set<ConsultaSummaryDTO> consultas;

    private Cargo cargo;

}
