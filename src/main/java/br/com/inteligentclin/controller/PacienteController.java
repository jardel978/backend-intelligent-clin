package br.com.inteligentclin.controller;

import br.com.inteligentclin.controller.exception.ConstraintException;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

//    @Autowired
//    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteModelDTO salvar(@Valid @RequestBody PacienteModelDTO pacienteDTO, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

            return pacienteService.salvar(pacienteDTO);
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

//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void atualizar(@PathVariable("id") Long id, @Valid @RequestBody Paciente paciente, BindingResult bgresult) {
//        if (bgresult.hasErrors())
//            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
//
////        pacienteService.buscarPorId(id)
////                .map((pacienteDaBase) -> {
////                    modelMapper.map(paciente, pacienteDaBase);
////                    pacienteService.salvar(pacienteDaBase);
////                    return Void.TYPE;
////                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
////                        "Paciente não encontrado")
////                );
//        Paciente pacienteDaBase = pacienteService.buscarPorId(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
//        pacienteDaBase.setNome(paciente.getNome());
//        pacienteDaBase.setSobrenome(paciente.getSobrenome());
//        pacienteDaBase.setDataCadastro(paciente.getDataCadastro());
//        pacienteDaBase.setCpf(paciente.getCpf());
//        pacienteDaBase.setEmail(paciente.getEmail());
//        pacienteDaBase.setTelefone(paciente.getTelefone());
//        pacienteDaBase.setDataNascimento(paciente.getDataNascimento());
//        pacienteDaBase.setEndereco(paciente.getEndereco());
//        pacienteDaBase.setConsultas(paciente.getConsultas());
//        pacienteDaBase.setProntuario(paciente.getProntuario());
//        pacienteDaBase.setSexo(paciente.getSexo());
//
//        pacienteService.salvar(pacienteDaBase);
//    }

}

