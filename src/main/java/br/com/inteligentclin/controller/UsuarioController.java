package br.com.inteligentclin.controller;

import br.com.inteligentclin.controller.exception.ConstraintException;
import br.com.inteligentclin.dtos.usuarioDTO.UsuarioModelDTO;
import br.com.inteligentclin.dtos.usuarioDTO.UsuarioSummaryDTO;
import br.com.inteligentclin.service.UsuarioService;
import br.com.inteligentclin.service.exception.DadoExistenteException;
import br.com.inteligentclin.service.exception.EntidadeRelacionadaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModelDTO salvar(@Valid @RequestBody UsuarioModelDTO usuarioDTO, BindingResult bgresult) throws DadoExistenteException {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        return usuarioService.salvar(usuarioDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioModelDTO buscarPorId(@PathVariable("id") Long id) {
        return usuarioService.buscarPorId(id).get();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UsuarioSummaryDTO> buscarTodos(Pageable pageable) {
        return usuarioService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) throws EntidadeRelacionadaException {
        usuarioService.excluirPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioModelDTO usuarioDTO,
                          BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        usuarioService.atualizar(id, usuarioDTO);
    }

}
