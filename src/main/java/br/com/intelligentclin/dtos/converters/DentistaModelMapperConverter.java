package br.com.intelligentclin.dtos.converters;

import br.com.intelligentclin.dtos.dentistaDTO.DentistaModelDTO;
import br.com.intelligentclin.dtos.dentistaDTO.DentistaSummaryDTO;
import br.com.intelligentclin.entity.Dentista;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class DentistaModelMapperConverter extends GenericModelMapperConverter<Dentista, DentistaModelDTO,
        DentistaSummaryDTO> {

}
