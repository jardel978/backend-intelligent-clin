package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.enderecoDTO.EnderecoModelDTO;
import br.com.inteligentclin.dtos.enderecoDTO.EnderecoSummaryDTO;
import br.com.inteligentclin.entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoModelMapperConverter extends GenericModelMapperConverter<Endereco, EnderecoModelDTO,
        EnderecoSummaryDTO>{

}
