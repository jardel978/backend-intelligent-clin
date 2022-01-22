package br.com.intelligentclin.dtos.converters;

import br.com.intelligentclin.dtos.enderecoDTO.EnderecoModelDTO;
import br.com.intelligentclin.dtos.enderecoDTO.EnderecoSummaryDTO;
import br.com.intelligentclin.entity.Endereco;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class EnderecoModelMapperConverter extends GenericModelMapperConverter<Endereco, EnderecoModelDTO,
        EnderecoSummaryDTO> {

}
