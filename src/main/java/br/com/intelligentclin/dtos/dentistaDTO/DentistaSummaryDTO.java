package br.com.intelligentclin.dtos.dentistaDTO;

import br.com.intelligentclin.dtos.PessoaSummaryDTO;
import br.com.intelligentclin.entity.enums.Especialidade;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DentistaSummaryDTO extends PessoaSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "O número de matrícula do dentista é obrigatório.")
    private String matricula;

    private List<Especialidade> especialidades;

    private Boolean temConsultas;

}
