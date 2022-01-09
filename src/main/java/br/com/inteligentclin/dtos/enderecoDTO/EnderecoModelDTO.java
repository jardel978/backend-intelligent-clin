package br.com.inteligentclin.dtos.enderecoDTO;

import br.com.inteligentclin.entity.Paciente;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoModelDTO extends EnderecoSummaryDTO implements Serializable {

    private String complemento;
    private Set<Paciente> pacientes;

}
