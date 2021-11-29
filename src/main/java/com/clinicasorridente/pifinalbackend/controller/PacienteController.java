package com.clinicasorridente.pifinalbackend.controller;

import com.clinicasorridente.pifinalbackend.controller.exception.ConstraintException;
import com.clinicasorridente.pifinalbackend.entity.Paciente;
import com.clinicasorridente.pifinalbackend.service.EnderecoService;
import com.clinicasorridente.pifinalbackend.service.PacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

//    @Autowired
//    private EnderecoService enderecoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paciente salvar(@Valid @RequestBody Paciente paciente, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        return pacienteService.salvar(paciente);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Paciente buscarPorId(@PathVariable("id") Long id) {
        return pacienteService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Paciente não encontrado")
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Paciente> buscarTodos() {
        return pacienteService.buscarTodos();
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable("id") Long id, @Valid @RequestBody Paciente paciente, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        pacienteService.buscarPorId(id)
                .map((pacienteDaBase) -> {
                    modelMapper.map(paciente, pacienteDaBase);
                    pacienteService.salvar(pacienteDaBase);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente não encontrado")
                );
    }

}

