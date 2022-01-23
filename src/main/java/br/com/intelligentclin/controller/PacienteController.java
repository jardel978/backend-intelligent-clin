package br.com.intelligentclin.controller;

import br.com.intelligentclin.controller.exception.ConstraintException;
import br.com.intelligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.intelligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import br.com.intelligentclin.service.PacienteService;
import br.com.intelligentclin.service.exception.EntidadeRelacionadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController()
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    @Transactional
    public ResponseEntity<PacienteModelDTO> salvar(@Valid @RequestBody PacienteModelDTO pacienteDTO, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        PacienteModelDTO pacienteSalvo = pacienteService.salvar(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteModelDTO> buscarPorId(@PathVariable("id") Long id) {
        PacienteModelDTO paciente = pacienteService.buscarPorId(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(paciente);
    }

    @GetMapping("/custom")
    public ResponseEntity<Page<PacienteModelDTO>> buscarCustomizado(Pageable pageable,
                                                                    @RequestParam(value = "id", required = false) Long id,
                                                                    @RequestParam(value = "nome", required = false) String nome,
                                                                    @RequestParam(value = "sobrenome", required = false) String sobrenome,
                                                                    @RequestParam(value = "cpf", required = false) String cpf) {
        Page<PacienteModelDTO> page = pacienteService.buscarCustomizado(pageable, id, nome, sobrenome, cpf);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping
    public ResponseEntity<Page<PacienteSummaryDTO>> buscarTodos(Pageable pageable) {
        Page<PacienteSummaryDTO> page = pacienteService.buscarTodos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") Long id) throws EntidadeRelacionadaException {
        pacienteService.excluirPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody PacienteModelDTO pacienteDTO,
                                       BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        pacienteService.atualizar(id, pacienteDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

