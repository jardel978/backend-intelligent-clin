package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.consultaDTO.ConsultaModelDTO;
import br.com.inteligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.inteligentclin.entity.Consulta;
import org.springframework.stereotype.Component;

@Component
public class ConsultaModelMapperConverter extends GenericModelMapperConverter<Consulta, ConsultaModelDTO,
        ConsultaSummaryDTO> {

    public ConsultaSummaryDTO converterCosultaToSummaryDTO(Consulta consulta) {
        return ConsultaSummaryDTO.builder()
                .id(consulta.getId())
                .idPaciente(consulta.getPaciente().getId())
                .idDentista(consulta.getDentista().getId())
                .idUsuario(consulta.getUsuario().getId())
                .dataConsulta(consulta.getDataConsulta())
                .horaConsulta(consulta.getHoraConsulta()).build();
    }

}
