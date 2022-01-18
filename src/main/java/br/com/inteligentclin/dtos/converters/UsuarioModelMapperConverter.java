package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.usuarioDTO.UsuarioModelDTO;
import br.com.inteligentclin.dtos.usuarioDTO.UsuarioSummaryDTO;
import br.com.inteligentclin.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelMapperConverter extends GenericModelMapperConverter<Usuario, UsuarioModelDTO,
        UsuarioSummaryDTO> {

}
