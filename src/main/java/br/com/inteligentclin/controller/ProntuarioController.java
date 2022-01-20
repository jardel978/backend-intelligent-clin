package br.com.inteligentclin.controller;

import br.com.inteligentclin.controller.exception.ConstraintException;
import br.com.inteligentclin.dtos.prontuarioDTO.ProntuarioModelDTO;
import br.com.inteligentclin.dtos.prontuarioDTO.ProntuarioSummaryDTO;
import br.com.inteligentclin.service.ProntuarioService;
import br.com.inteligentclin.service.exception.ValidadeProntuarioException;
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

    @PostMapping("/salvar")
    @Transactional
    public ResponseEntity<ProntuarioModelDTO> salvar(
            @RequestBody ProntuarioModelDTO prontuarioDTO,
            @Valid @RequestParam(value = "idPaciente") Long idPaciente,
            @Valid @RequestParam(value = "idDentista") Long idDentista,
            BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        ProntuarioModelDTO prontuarioSalvo = prontuarioService.salvar(prontuarioDTO, idPaciente, idDentista);
        return ResponseEntity.status(HttpStatus.CREATED).body(prontuarioSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProntuarioModelDTO> buscarPorId(@PathVariable("id") Long id) {
        ProntuarioModelDTO prontuario = prontuarioService.buscarPorId(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(prontuario);
    }

    @GetMapping
    public ResponseEntity<Page<ProntuarioSummaryDTO>> buscarTodos(Pageable pageable) {
        Page<ProntuarioSummaryDTO> page = prontuarioService.buscarTodos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluirPorId(@PathVariable("id") Long id) throws ValidadeProntuarioException {
        prontuarioService.excluirPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity<?> atualizar(@RequestBody ProntuarioModelDTO prontuarioDTO,
                                       @Valid @RequestParam(value = "idPaciente") Long idPaciente,
                                       @Valid @RequestParam(value = "idDentista") Long idDentista,
                                       BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        prontuarioService.salvar(prontuarioDTO, idPaciente, idDentista);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
