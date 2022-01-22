package br.com.intelligentclin.dtos.pacienteDTO;

import br.com.intelligentclin.dtos.PessoaModelDTO;
import br.com.intelligentclin.dtos.consultaDTO.ConsultaMixClasseModelDTO;
import br.com.intelligentclin.entity.Endereco;
import br.com.intelligentclin.entity.Idade;
import br.com.intelligentclin.entity.Prontuario;
import br.com.intelligentclin.entity.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteModelDTO extends PessoaModelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    private Idade idade;

    private Endereco endereco;

    private Set<ConsultaMixClasseModelDTO> consultas;

    private Prontuario prontuario;

    private Sexo sexo;

}
