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
    public ProntuarioModelDTO salvar(
            @RequestBody ProntuarioModelDTO prontuarioDTO,
            @Valid @RequestParam(value = "idPaciente") Long idPaciente,
            @Valid @RequestParam(value = "idDentista") Long idDentista,
            BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        return prontuarioService.salvar(prontuarioDTO, idPaciente, idDentista);
    }

    @GetMapping("/{id}")
    public ProntuarioModelDTO buscarPorId(@PathVariable("id") Long id) {
        return prontuarioService.buscarPorId(id).get();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProntuarioSummaryDTO> buscarTodos(Pageable pageable) {
        return prontuarioService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    public void excluirPorId(Long id) throws ValidadeProntuarioException {
        prontuarioService.excluirPorId(id);
    }
}
