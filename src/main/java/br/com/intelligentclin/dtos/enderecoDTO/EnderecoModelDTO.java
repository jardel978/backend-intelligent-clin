package br.com.intelligentclin.dtos.enderecoDTO;

import br.com.intelligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoModelDTO extends EnderecoSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String complemento;
    private Set<PacienteSummaryDTO> pacientes;

}
