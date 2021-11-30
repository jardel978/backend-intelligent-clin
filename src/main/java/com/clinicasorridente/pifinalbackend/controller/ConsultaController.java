package com.clinicasorridente.pifinalbackend.controller;

import com.clinicasorridente.pifinalbackend.controller.exception.ConstraintException;
import com.clinicasorridente.pifinalbackend.dtos.ConsultaDTO;
import com.clinicasorridente.pifinalbackend.entity.Consulta;
import com.clinicasorridente.pifinalbackend.entity.Usuario;
import com.clinicasorridente.pifinalbackend.service.ConsultaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaDTO salvar(@Valid @RequestBody Consulta consulta, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        consultaService.salvar(consulta);
        ConsultaDTO consultaDTO = converterDTO(consulta);
        return consultaDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Consulta buscarPorId(@PathVariable("id") Long id) {
        return consultaService.buscarPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaDTO> buscarTodos() {
        List<Consulta> lista = consultaService.buscarTodos();
        List<ConsultaDTO> listaDTO = lista.stream().map(consulta -> converterDTO(consulta)).collect(Collectors.toList());
        return listaDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) {
        consultaService.buscarPorId(id)
                .map(consulta -> {
                    consultaService.excluirPorId(consulta.getId());
                    return Void.TYPE;
                        }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable("id") Long id, @Valid @RequestBody Consulta consulta, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

//        consultaService.buscarPorId(id)
//                .map(consultaDaBase -> {
//                    modelMapper.map(consulta, consultaDaBase);
//                    consultaService.salvar(consultaDaBase);
//                    return Void.TYPE;
//                }).orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada.")
//                );
        Consulta consultaDaBase = consultaService.buscarPorId(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada.")
                );
        consultaDaBase.setPaciente(consulta.getPaciente());
        consultaDaBase.setDentista(consulta.getDentista());
        consultaDaBase.setUsuario(consulta.getUsuario());
        consultaDaBase.setDataConsulta(consulta.getDataConsulta());
        consultaDaBase.setHoraConsulta(consulta.getHoraConsulta());
        consultaService.salvar(consultaDaBase);
    }

    private ConsultaDTO converterDTO(Consulta consulta) {
        return ConsultaDTO.builder()
                .id(consulta.getId())
                .idPaciente(consulta.getPaciente().getId())
                .idDentista(consulta.getDentista().getId())
                .idUsuario(consulta.getUsuario().getId())
                .dataConsulta(consulta.getDataConsulta())
                .horaConsulta(consulta.getHoraConsulta()).build();
    }

}
