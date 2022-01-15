package br.com.inteligentclin.dtos.enderecoDTO;

import br.com.inteligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import br.com.inteligentclin.entity.Paciente;
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
//    private Set<Paciente> pacientes;

}
