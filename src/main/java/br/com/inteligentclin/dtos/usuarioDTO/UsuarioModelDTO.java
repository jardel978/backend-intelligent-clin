package br.com.inteligentclin.dtos.usuarioDTO;

import br.com.inteligentclin.entity.Consulta;
import br.com.inteligentclin.entity.enums.Cargo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Builder
@Getter
@Setter
public class UsuarioModelDTO implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Cargo acesso;
    private Set<Consulta> consultas;


}
