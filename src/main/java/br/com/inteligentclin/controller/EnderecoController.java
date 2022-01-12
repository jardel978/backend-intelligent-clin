package br.com.inteligentclin.controller;

import br.com.inteligentclin.controller.exception.ConstraintException;
import br.com.inteligentclin.dtos.enderecoDTO.EnderecoModelDTO;
import br.com.inteligentclin.dtos.enderecoDTO.EnderecoSummaryDTO;
import br.com.inteligentclin.entity.Endereco;
import br.com.inteligentclin.service.EnderecoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

//    @PostMapping
//    @Transactional
//    @ResponseStatus(HttpStatus.CREATED)
//    public EnderecoModelDTO salvar(@Valid @RequestBody EnderecoModelDTO enderecoDTO) {
//        return enderecoService.salvar(enderecoDTO);
//    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EnderecoModelDTO buscarPorId(@PathVariable("id") Long id) {
        return enderecoService.buscarPorId(id).get();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<EnderecoSummaryDTO> buscarTodos(Pageable pageable) {
        return enderecoService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) {
        try {
            enderecoService.buscarPorId(id)
                    .map(endereco -> {
                        enderecoService.excluirPorId(endereco.getId());
                        return Void.TYPE;
                    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Endereço não encontrado")
                    );
        } catch (
                DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um endereço que está vinculado a um " +
                    "paciente.");
        }
    }

    @PutMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable("id") Long id, @RequestBody EnderecoModelDTO enderecoDTO) {
        enderecoService.atualizar(id, enderecoDTO);
    }

}

