package br.com.intelligentclin.controller;

import br.com.intelligentclin.controller.exception.ConstraintException;
import br.com.intelligentclin.dtos.prontuarioDTO.ProntuarioModelDTO;
import br.com.intelligentclin.dtos.prontuarioDTO.ProntuarioSummaryDTO;
import br.com.intelligentclin.service.ProntuarioService;
import br.com.intelligentclin.service.exception.ValidadeProntuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    @Autowired
    private ProntuarioService prontuarioService;

    @Transactional
    @PostMapping("/permitAll/cadastrar")
    public ResponseEntity<ProntuarioModelDTO> salvar(
            @RequestBody ProntuarioModelDTO prontuarioDTO,
            @Valid @RequestParam(value = "id-paciente") Long idPaciente,
            @Valid @RequestParam(value = "id-dentista") Long idDentista,
            BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        ProntuarioModelDTO prontuarioSalvo = prontuarioService.salvar(prontuarioDTO, idPaciente, idDentista);
        return ResponseEntity.status(HttpStatus.CREATED).body(prontuarioSalvo);
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<ProntuarioModelDTO> buscarPorId(@PathVariable("id") Long id) {
        ProntuarioModelDTO prontuario = prontuarioService.buscarPorId(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(prontuario);
    }

    @GetMapping("/permitAll/todos")
    public ResponseEntity<Page<ProntuarioSummaryDTO>> buscarTodos(Pageable pageable) {
        Page<ProntuarioSummaryDTO> page = prontuarioService.buscarTodos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Transactional
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") Long id) throws ValidadeProntuarioException {
        prontuarioService.excluirPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity<?> atualizar(@RequestBody ProntuarioModelDTO prontuarioDTO,
                                       @Valid @RequestParam(value = "id-paciente") Long idPaciente,
                                       @Valid @RequestParam(value = "id-dentista") Long idDentista,
                                       BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        prontuarioService.salvar(prontuarioDTO, idPaciente, idDentista);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
