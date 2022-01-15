package br.com.inteligentclin.controller;

import br.com.inteligentclin.controller.exception.ConstraintException;
import br.com.inteligentclin.dtos.consultaDTO.ConsultaModelDTO;
import br.com.inteligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.inteligentclin.dtos.converters.ConsultaModelMapperConverter;
import br.com.inteligentclin.entity.Consulta;
import br.com.inteligentclin.service.ConsultaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaModelMapperConverter consultaConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consulta salvar(@Valid @RequestBody Consulta consulta, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        return consultaService.salvar(consulta);
//        ConsultaModelDTO consultaModelDTO = consultaConverter.co(consulta);
//        return consultaModelDTO;
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
    public List<ConsultaSummaryDTO> buscarTodos() {
        List<Consulta> lista = consultaService.buscarTodos();
        List<ConsultaSummaryDTO> listaDTO =
                lista.stream().map(consulta ->
                        consultaConverter.converterCosultaToSummaryDTO(consulta)).collect(Collectors.toList());
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
}
