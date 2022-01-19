package br.com.inteligentclin.controller;

import br.com.inteligentclin.dtos.consultaDTO.ConsultaModelDTO;
import br.com.inteligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.inteligentclin.dtos.converters.ConsultaModelMapperConverter;
import br.com.inteligentclin.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaModelMapperConverter consultaConverter;

    @PostMapping("/salvar")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaModelDTO salvar(@RequestBody ConsultaModelDTO consultaDTO,
                                   @RequestParam(value = "idPaciente") Long idPaciente,
                                   @RequestParam(value = "idDentista") Long idDentista,
                                   @RequestParam(value = "idUsuario") Long idUsuario) {
        return consultaService.salvar(consultaDTO, idPaciente, idDentista, idUsuario);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaModelDTO buscarPorId(@PathVariable("id") Long id) {
        return consultaService.buscarPorId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ConsultaSummaryDTO> buscarTodos(Pageable pageable) {
        return consultaService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) {
        consultaService.excluirPorId(id);
//        consultaService.buscarPorId(id)
//                .map(consulta -> {
//                    consultaService.excluirPorId(consulta.getId());
//                    return Void.TYPE;
//                }).orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
    }

    @PutMapping("/atualizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@RequestParam("idConsulta") Long idConsulta, @RequestBody ConsultaModelDTO consultaDTO,
                          @RequestParam(value = "idPaciente") Long idPaciente,
                          @RequestParam(value = "idDentista") Long idDentista,
                          @RequestParam(value = "idUsuario") Long idUsuario) {
        consultaService.atualizar(idConsulta, consultaDTO, idPaciente, idDentista, idUsuario);
    }
}
