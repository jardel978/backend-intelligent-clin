package br.com.inteligentclin.controller;

import br.com.inteligentclin.controller.exception.ConstraintException;
import br.com.inteligentclin.dtos.usuarioDTO.UsuarioModelDTO;
import br.com.inteligentclin.dtos.usuarioDTO.UsuarioSummaryDTO;
import br.com.inteligentclin.service.UsuarioService;
import br.com.inteligentclin.service.exception.DadoExistenteException;
import br.com.inteligentclin.service.exception.EntidadeRelacionadaException;
import br.com.inteligentclin.service.exception.ParametroRequeridoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModelDTO salvar(@Valid @RequestBody UsuarioModelDTO usuarioDTO, BindingResult bgresult) throws DadoExistenteException {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        return usuarioService.salvar(usuarioDTO);
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam(value = "login", required = false) String login,
                                                @RequestParam(value = "email", required = false) String email,
                                                @RequestParam(value = "senha", required = false) String senha) throws ParametroRequeridoException {
        boolean valido = usuarioService.validarSenha(login, email, senha);
        if (valido)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioModelDTO buscarPorId(@PathVariable("id") Long id) {
        return usuarioService.buscarPorId(id).get();
    }

    @GetMapping("/custom")
    @ResponseStatus(HttpStatus.OK)
    public Page<UsuarioModelDTO> buscarCustomizado(Pageable pageable,
                                                   @RequestParam(value = "id", required = false) Long id,
                                                   @RequestParam(value = "nome", required = false) String nome,
                                                   @RequestParam(value = "sobrenome", required = false) String sobrenome,
                                                   @RequestParam(value = "cpf", required = false) String cpf) {
        return usuarioService.buscarCustomizado(pageable, id, nome, sobrenome, cpf);
    }

    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioModelDTO> buscarPorEmail(@RequestParam(value = "email") String email) {
        return usuarioService.buscarPorEmail(email);
    }

    @GetMapping("/cargos")
    @ResponseStatus(HttpStatus.OK)
    List<UsuarioModelDTO> buscarProCargo(@RequestParam(value = "cargo") String cargo) {
        return usuarioService.buscarPorCargo(cargo);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UsuarioSummaryDTO> buscarTodos(Pageable pageable) {
        return usuarioService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) throws EntidadeRelacionadaException {
        usuarioService.excluirPorId(id);
    }

    @PutMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioModelDTO usuarioDTO,
                          BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        usuarioService.atualizar(id, usuarioDTO);
    }

}
