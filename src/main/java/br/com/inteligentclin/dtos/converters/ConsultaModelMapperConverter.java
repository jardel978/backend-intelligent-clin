package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.consultaDTO.ConsultaModelDTO;
import br.com.inteligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.inteligentclin.entity.Consulta;
import org.springframework.stereotype.Component;

@Component
public class ConsultaModelMapperConverter extends GenericModelMapperConverter<Consulta, ConsultaModelDTO,
        ConsultaSummaryDTO> {


}
