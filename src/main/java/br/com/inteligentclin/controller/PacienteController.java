package br.com.inteligentclin.controller;

import br.com.inteligentclin.controller.exception.ConstraintException;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteSummaryDTO;
import br.com.inteligentclin.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController()
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteModelDTO salvar(@Valid @RequestBody PacienteModelDTO pacienteDTO, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        return pacienteService.salvar(pacienteDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PacienteModelDTO buscarPorId(@PathVariable("id") Long id) {
        return pacienteService.buscarPorId(id).get();
    }

    @GetMapping("/custom")
    @ResponseStatus(HttpStatus.OK)
    public Page<PacienteModelDTO> buscarCustomizado(Pageable pageable,
                                                    @RequestParam(value = "id", required = false) Long id,
                                                    @RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "sobrenome", required = false) String sobrenome,
                                                    @RequestParam(value = "cpf", required = false) String cpf) {
        return pacienteService.buscarCustomizado(pageable, id, nome, sobrenome, cpf);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PacienteSummaryDTO> buscarTodos(Pageable pageable) {
        return pacienteService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) {
        pacienteService.buscarPorId(id)
                .map(paciente -> {
                    pacienteService.excluirPorId(paciente.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente não encontrado")
                );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable("id") Long id, @Valid @RequestBody PacienteModelDTO pacienteDTO,
                          BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        pacienteService.atualizar(id, pacienteDTO);
    }

}

