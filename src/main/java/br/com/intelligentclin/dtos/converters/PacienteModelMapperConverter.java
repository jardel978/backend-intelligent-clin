package br.com.intelligentclin.dtos.converters;

import br.com.intelligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.intelligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import br.com.intelligentclin.entity.Paciente;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PacienteModelMapperConverter extends GenericModelMapperConverter<Paciente, PacienteModelDTO, PacienteSummaryDTO> {


}
