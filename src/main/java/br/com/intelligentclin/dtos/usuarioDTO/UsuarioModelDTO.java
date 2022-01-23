package br.com.intelligentclin.dtos.usuarioDTO;

import br.com.intelligentclin.dtos.PessoaModelDTO;
import br.com.intelligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.intelligentclin.entity.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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

    @Size(min = 6, max = 15, message = "Sua senha deve ter no mínimo 6 dígitos.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//ignorar senha no retorno
    private String senha;

    //    @JsonManagedReference
    @JsonIgnore
    private Set<ConsultaSummaryDTO> consultas;

    private Cargo cargo;

}
