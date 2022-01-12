package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.enderecoDTO.EnderecoModelDTO;
import br.com.inteligentclin.dtos.enderecoDTO.EnderecoSummaryDTO;
import br.com.inteligentclin.entity.Endereco;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class EnderecoModelMapperConverter extends GenericModelMapperConverter<Endereco, EnderecoModelDTO,
        EnderecoSummaryDTO> {

}
