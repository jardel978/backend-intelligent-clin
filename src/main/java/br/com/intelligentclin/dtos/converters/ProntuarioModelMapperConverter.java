package br.com.intelligentclin.dtos.converters;

import br.com.intelligentclin.dtos.prontuarioDTO.ProntuarioModelDTO;
import br.com.intelligentclin.dtos.prontuarioDTO.ProntuarioSummaryDTO;
import br.com.intelligentclin.entity.Prontuario;
import org.springframework.stereotype.Component;

@Component
public class ProntuarioModelMapperConverter extends GenericModelMapperConverter<Prontuario, ProntuarioModelDTO,
        ProntuarioSummaryDTO> {

}
