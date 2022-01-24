package br.com.intelligentclin.controller;

import br.com.intelligentclin.controller.exception.ConstraintException;
import br.com.intelligentclin.dtos.dentistaDTO.DentistaModelDTO;
import br.com.intelligentclin.dtos.dentistaDTO.DentistaSummaryDTO;
import br.com.intelligentclin.entity.enums.Especialidade;
import br.com.intelligentclin.service.DentistaService;
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

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    @Autowired
    private DentistaService dentistaService;

    @Transactional
    @PostMapping("/cadastrar")
    public ResponseEntity<DentistaModelDTO> salvar(@Valid @RequestBody DentistaModelDTO dentistaDTO, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        DentistaModelDTO dentistaSalvo = dentistaService.salvar(dentistaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dentistaSalvo);
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<DentistaModelDTO> buscarPorId(@PathVariable("id") Long id) {
        DentistaModelDTO dentista = dentistaService.buscarPorId(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(dentista);
    }

    @GetMapping("/buscar-custom")
    public ResponseEntity<Page<DentistaModelDTO>> buscarCustomizado(Pageable pageable,
                                                                    @RequestParam(value = "id", required = false) Long id,
                                                                    @RequestParam(value = "nome", required = false) String nome,
                                                                    @RequestParam(value = "sobrenome", required = false) String sobrenome,
                                                                    @RequestParam(value = "cpf", required = false) String cpf) {
        Page<DentistaModelDTO> page = dentistaService.buscarCustomizado(pageable, id, nome, sobrenome, cpf);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/buscar-matricula/{matricula}")
    public ResponseEntity<DentistaModelDTO> buscarPorMatricula(@PathVariable("matricula") String numMatricula) {
        DentistaModelDTO dentista = dentistaService.buscarPorMatricula(numMatricula);
        return ResponseEntity.status(HttpStatus.OK).body(dentista);
    }

    @GetMapping("/permitAll/especialidades")
    public ResponseEntity<Page<DentistaSummaryDTO>> buscarPorEspecialidade(
            Pageable pageable,
            @RequestParam(value = "especialidade") String nomeEspecialidade) {
        try {
            Especialidade stringParaEnumEspecialidade = Especialidade.valueOf(nomeEspecialidade.toUpperCase());
            Page<DentistaSummaryDTO> page = dentistaService.buscarPorEspecialidade(pageable,
                    stringParaEnumEspecialidade);
            return ResponseEntity.status(HttpStatus.OK).body(page);
        } catch (RuntimeException e) {
            throw new RuntimeException("Nenhum Dentista registrado possuí a especialidade informada.");
        }
    }

    @GetMapping("/permitAll/todos")
    public ResponseEntity<Page<DentistaSummaryDTO>> buscarTodos(Pageable pageable) {
        Page<DentistaSummaryDTO> page = dentistaService.buscarTodos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Transactional
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") Long id) throws EntidadeRelacionadaException {
        dentistaService.excluirPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody DentistaModelDTO dentistaDTO, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        dentistaService.atualizar(id, dentistaDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
