package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.prontuarioDTO.ProntuarioModelDTO;
import br.com.inteligentclin.dtos.prontuarioDTO.ProntuarioSummaryDTO;
import br.com.inteligentclin.entity.Prontuario;
import org.springframework.stereotype.Component;

@Component
public class ProntuarioModelMapperConverter extends GenericModelMapperConverter<Prontuario, ProntuarioModelDTO,
        ProntuarioSummaryDTO> {

}
