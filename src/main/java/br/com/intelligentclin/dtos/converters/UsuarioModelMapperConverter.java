package br.com.intelligentclin.dtos.converters;

import br.com.intelligentclin.dtos.usuarioDTO.UsuarioModelDTO;
import br.com.intelligentclin.dtos.usuarioDTO.UsuarioSummaryDTO;
import br.com.intelligentclin.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelMapperConverter extends GenericModelMapperConverter<Usuario, UsuarioModelDTO,
        UsuarioSummaryDTO> {

}
