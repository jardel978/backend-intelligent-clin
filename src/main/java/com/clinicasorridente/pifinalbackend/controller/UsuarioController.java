package com.clinicasorridente.pifinalbackend.controller;

import com.clinicasorridente.pifinalbackend.controller.exception.ConstraintException;
import com.clinicasorridente.pifinalbackend.dtos.UsuarioDTO;
import com.clinicasorridente.pifinalbackend.entity.Usuario;
import com.clinicasorridente.pifinalbackend.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@Valid @RequestBody Usuario usuario, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        return usuarioService.salvar(usuario);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario buscarPorId(@PathVariable("id") Long id) {
        return usuarioService.buscarPorId(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> buscarTodos() {
//        List<Usuario> lista = usuarioService.buscarTodos();
//        List<UsuarioDTO> listaDTO = lista.stream().map(usuario -> UsuarioDTO.builder()
//                .id(usuario.getId())
//                .nome(usuario.getNome())
//                .email(usuario.getEmail())
//                .acesso(usuario.getAcesso()).build()
//        ).collect(Collectors.toList());
//        return listaDTO;
        return usuarioService.buscarTodos();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) {
        usuarioService.buscarPorId(id)
                .map(usuario -> {
                    usuarioService.excluirPorId(usuario.getId());
                    return Void.TYPE;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
                );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable("id") Long id, @Valid @RequestBody Usuario usuario, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

//        usuarioService.buscarPorId(id)
//                .map(usuarioDaBase -> {
//                    modelMapper.map(usuario, usuarioDaBase);
//                    usuarioService.salvar(usuarioDaBase);
//                    return Void.TYPE;
//                }).orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
//                );
        Usuario usuarioDaBase = usuarioService.buscarPorId(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
                );
        usuarioDaBase.setNome(usuario.getNome());
        usuarioDaBase.setEmail(usuario.getEmail());
        usuarioDaBase.setSenha(usuario.getSenha());
        usuarioDaBase.setAcesso(usuario.getAcesso());
        usuarioService.salvar(usuarioDaBase);
    }

}
