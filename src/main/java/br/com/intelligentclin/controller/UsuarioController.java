package br.com.intelligentclin.controller;

import br.com.intelligentclin.controller.exception.ConstraintException;
import br.com.intelligentclin.dtos.usuarioDTO.UsuarioModelDTO;
import br.com.intelligentclin.dtos.usuarioDTO.UsuarioSummaryDTO;
import br.com.intelligentclin.service.UsuarioService;
import br.com.intelligentclin.service.exception.DadoExistenteException;
import br.com.intelligentclin.service.exception.EntidadeRelacionadaException;
import br.com.intelligentclin.service.exception.ParametroRequeridoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<UsuarioModelDTO> salvar(@Valid @RequestBody UsuarioModelDTO usuarioDTO, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        UsuarioModelDTO usuarioSalvo = usuarioService.salvar(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<UsuarioModelDTO> buscarPorId(@PathVariable("id") Long id) {
        UsuarioModelDTO usuarioSalvo = usuarioService.buscarPorId(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(usuarioSalvo);
    }

    @GetMapping("/buscar-custom")
    public ResponseEntity<Page<UsuarioModelDTO>> buscarCustomizado(Pageable pageable,
                                                                   @RequestParam(value = "id", required = false) Long id,
                                                                   @RequestParam(value = "nome", required = false) String nome,
                                                                   @RequestParam(value = "sobrenome", required = false) String sobrenome,
                                                                   @RequestParam(value = "cpf", required = false) String cpf) {
        Page<UsuarioModelDTO> page = usuarioService.buscarCustomizado(pageable, id, nome, sobrenome, cpf);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/buscar-email")
    public ResponseEntity<List<UsuarioModelDTO>> buscarPorEmail(@RequestParam(value = "email") String email) {
        List<UsuarioModelDTO> lista = usuarioService.buscarPorEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/buscar-cargos")
    public ResponseEntity<List<UsuarioModelDTO>> buscarProCargo(@RequestParam(value = "cargo") String cargo) {
        List<UsuarioModelDTO> lista = usuarioService.buscarPorCargo(cargo);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<Page<UsuarioSummaryDTO>> buscarTodos(Pageable pageable) {
        Page<UsuarioSummaryDTO> page = usuarioService.buscarTodos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity<?> excluirPorId(@PathVariable("id") Long id) throws EntidadeRelacionadaException {
        usuarioService.excluirPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioModelDTO usuarioDTO,
                                       BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        usuarioService.atualizar(id, usuarioDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/validar-senha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam(value = "email") String email,
                                                @RequestParam(value = "senha") String senha) throws ParametroRequeridoException {
        boolean valido = usuarioService.validarSenha(email, senha);
        if (valido)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws ParametroRequeridoException, IOException {
        usuarioService.refreshToken(request, response);
    }

}
