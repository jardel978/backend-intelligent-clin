package br.com.intelligentclin.dtos.converters;

import br.com.intelligentclin.dtos.consultaDTO.ConsultaModelDTO;
import br.com.intelligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.intelligentclin.entity.Consulta;
import org.springframework.stereotype.Component;

@Component
public class ConsultaModelMapperConverter extends GenericModelMapperConverter<Consulta, ConsultaModelDTO,
        ConsultaSummaryDTO> {


}
