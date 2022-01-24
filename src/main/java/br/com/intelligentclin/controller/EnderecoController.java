package br.com.intelligentclin.controller;

import br.com.intelligentclin.dtos.enderecoDTO.EnderecoModelDTO;
import br.com.intelligentclin.dtos.enderecoDTO.EnderecoSummaryDTO;
import br.com.intelligentclin.service.EnderecoService;
import br.com.intelligentclin.service.exception.EntidadeRelacionadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

//    @Transactional
//    @PostMapping("/permitAll/cadastrar")
//    public ResponseEntity<EnderecoModelDTO> salvar(@RequestBody EnderecoModelDTO enderecoDTO) {
//        EnderecoModelDTO enderecoSalvo = enderecoService.salvar(enderecoDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSalvo);
//    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<EnderecoModelDTO> buscarPorId(@PathVariable("id") Long id) {
        EnderecoModelDTO endereco = enderecoService.buscarPorId(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @GetMapping("/permitAll/todos")
    public ResponseEntity<Page<EnderecoSummaryDTO>> buscarTodos(Pageable pageable) {
        Page<EnderecoSummaryDTO> page = enderecoService.buscarTodos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Transactional
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") Long id) throws EntidadeRelacionadaException {
        enderecoService.excluirPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody EnderecoModelDTO enderecoDTO) {
        enderecoService.atualizar(id, enderecoDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

