package br.com.intelligentclin.controller;

import br.com.intelligentclin.dtos.consultaDTO.ConsultaModelDTO;
import br.com.intelligentclin.dtos.consultaDTO.ConsultaSummaryDTO;
import br.com.intelligentclin.dtos.converters.ConsultaModelMapperConverter;
import br.com.intelligentclin.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaModelMapperConverter consultaConverter;

    @Transactional
    @PostMapping("/permitAll/cadastrar")
    public ResponseEntity<ConsultaModelDTO> salvar(@RequestBody ConsultaModelDTO consultaDTO,
                                                   @RequestParam(value = "id-paciente") Long idPaciente,
                                                   @RequestParam(value = "id-dentista") Long idDentista,
                                                   @RequestParam(value = "id-usuario") Long idUsuario) {
        ConsultaModelDTO consultaSalva = consultaService.salvar(consultaDTO, idPaciente, idDentista, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaSalva);
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<ConsultaModelDTO> buscarPorId(@PathVariable("id") Long id) {
        ConsultaModelDTO consulta = consultaService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(consulta);
    }

    @GetMapping("/permitAll/todos")
    public Page<ConsultaSummaryDTO> buscarTodos(Pageable pageable) {
        return consultaService.buscarTodos(pageable);
    }

    @Transactional
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") Long id) {
        consultaService.excluirPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestParam("id-consulta") Long idConsulta,
                                       @RequestBody ConsultaModelDTO consultaDTO,
                                       @RequestParam(value = "id-paciente") Long idPaciente,
                                       @RequestParam(value = "id-dentista") Long idDentista,
                                       @RequestParam(value = "id-usuario") Long idUsuario) {
        consultaService.atualizar(idConsulta, consultaDTO, idPaciente, idDentista, idUsuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
