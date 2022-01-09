package br.com.inteligentclin.dtos.enderecoDTO;

import br.com.inteligentclin.entity.Paciente;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class EnderecoModelDTO {

    private Long id;
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private Set<Paciente> pacientes;


}
