package br.com.intelligentclin.dtos.prontuarioDTO;

import br.com.intelligentclin.dtos.dentistaDTO.DentistaSummaryDTO;
import br.com.intelligentclin.dtos.pacienteDTO.PacienteMixProntuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProntuarioSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "Por gentileza, informe a qual paciente pertence esse prontuário.")
    private PacienteMixProntuarioDTO paciente;

    @NotNull(message = "É necessário informar qual o dentista responsável pelo paciente dono desse prontuário.")
    private DentistaSummaryDTO dentista;

}
