package br.com.inteligentclin.controller;

import br.com.inteligentclin.controller.exception.ConstraintException;
import br.com.inteligentclin.dtos.dentistaDTO.DentistaModelDTO;
import br.com.inteligentclin.dtos.dentistaDTO.DentistaSummaryDTO;
import br.com.inteligentclin.entity.enums.Especialidade;
import br.com.inteligentclin.service.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    @Autowired
    private DentistaService dentistaService;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public DentistaModelDTO salvar(@Valid @RequestBody DentistaModelDTO dentistaDTO, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        return dentistaService.salvar(dentistaDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DentistaModelDTO buscarPorId(@PathVariable("id") Long id) {
        return dentistaService.buscarPorId(id).get();
    }

    //     http://localhost:8080/dentistas?size=1&page=0&custom?nome=nome&sobrenome=sobrenome (ou apenas nome, ou id,
    //     cpf...)
    @GetMapping("/custom")
    @ResponseStatus(HttpStatus.OK)
    public Page<DentistaModelDTO> buscarCustomizado(Pageable pageable,
                                                    @RequestParam(value = "id", required = false) Long id,
                                                    @RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "sobrenome", required = false) String sobrenome,
                                                    @RequestParam(value = "cpf", required = false) String cpf) {
        return dentistaService.buscarCustomizado(pageable, id, nome, sobrenome, cpf);
    }

    //    http://localhost:8080/dentistas/matriculas?matricula=153246
    @GetMapping("/matriculas")
    @ResponseStatus(HttpStatus.OK)
    public DentistaModelDTO buscarPorMatricula(@RequestParam(value = "matricula") String numMatricula) {
        return dentistaService.buscarPorMatricula(numMatricula);
    }

    //    http://localhost:8080/dentistas?size=2&page=0&especialidades?especialidade=clinico
    @GetMapping("/especialidades")
    @ResponseStatus(HttpStatus.OK)
    public Page<DentistaSummaryDTO> buscarPorEspecialidades(
            Pageable pageable,
            @RequestParam(value = "especialidade") String nomeEspecialidade) {
        try {
            Especialidade stringParaEnumEspecialidade = Especialidade.valueOf(nomeEspecialidade.toUpperCase());
            return dentistaService.buscarPorEspecialidades(pageable, stringParaEnumEspecialidade);
        } catch (RuntimeException e) {
            throw new RuntimeException("Nenhum Dentista registrado possuí a especialidade informada.");
        }
    }

    //    http://localhost:8080/dentistas?size=quantidade&page=numPágs
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DentistaSummaryDTO> buscarTodos(Pageable pageable) {
        return dentistaService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) {
        dentistaService.buscarPorId(id)
                .map(dentista -> {
                    dentistaService.excluirPorId(dentista.getId());
                    return Void.TYPE;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Dentista não encontrado.")
                );
    }

    @PutMapping("/{id}")
    @Transactional
//colocar isso em todos os métodos de excluir, salvar e atualizar (esse comentário = última modificação)
    @ResponseStatus(HttpStatus.OK)//mudar para esse httpStatus os outros métodos de delete, save, update
    public void atualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody DentistaModelDTO dentistaDTO, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        try {
            dentistaService.atualizar(id, dentistaDTO);
        } catch (ResponseStatusException e) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Dentista não encontrado.");
        }
    }
}
