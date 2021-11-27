package com.clinicasorridente.pifinalbackend.dtos;

import com.clinicasorridente.pifinalbackend.entity.CategoriaUsuario;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class UsuarioDTO implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private CategoriaUsuario acesso;

}
