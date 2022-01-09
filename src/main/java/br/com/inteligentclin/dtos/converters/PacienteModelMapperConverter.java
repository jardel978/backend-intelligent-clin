package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import br.com.inteligentclin.entity.Paciente;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PacienteModelMapperConverter extends GenericModelMapperConverter<Paciente, PacienteModelDTO, PacienteSummaryDTO> {


}
