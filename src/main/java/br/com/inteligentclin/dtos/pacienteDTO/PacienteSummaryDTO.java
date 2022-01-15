package br.com.inteligentclin.dtos.pacienteDTO;

import br.com.inteligentclin.dtos.PessoaSummaryDTO;
import br.com.inteligentclin.entity.Consulta;
import br.com.inteligentclin.entity.Idade;
import br.com.inteligentclin.entity.Prontuario;
import br.com.inteligentclin.entity.enums.Sexo;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteSummaryDTO extends PessoaSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

//    private LocalDate dataNascimento;

    private Idade idade;

    private Set<Consulta> consultas;

    private Prontuario prontuario;

    private Sexo sexo;

}
