package br.com.inteligentclin.controller;

import br.com.inteligentclin.dtos.enderecoDTO.EnderecoModelDTO;
import br.com.inteligentclin.entity.Endereco;
import br.com.inteligentclin.service.EnderecoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco salvar(@RequestBody Endereco endereco) {
        return enderecoService.salvar(endereco);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EnderecoModelDTO buscarPorId(@PathVariable("id") Long id) {
        return enderecoService.buscarPorId(id).get();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Endereco> buscarTodos() {
        return enderecoService.buscarTodos();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable("id") Long id) {
        enderecoService.buscarPorId(id)
                .map(endereco -> {
                    enderecoService.excluirPorId(endereco.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Endereço não encontrado")
                );
    }

//    @PutMapping("/{id}")
//    @Transactional
//    public void atualizar(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
////        enderecoService.buscarPorId(id)
////                .map(enderecoDaBase -> {
////                    modelMapper.map(endereco, enderecoDaBase);
////                    enderecoService.salvar(enderecoDaBase);
////                    return Void.TYPE;
////                }).orElseThrow(() ->
////                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado.")
////                );
//        Endereco enderecoDaBase = enderecoService.buscarPorId(id).orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado.")
//                );
//        enderecoDaBase.setRua(endereco.getRua());
//        enderecoDaBase.setNumero(endereco.getNumero());
//        enderecoDaBase.setCidade(endereco.getCidade());
//        enderecoDaBase.setEstado(endereco.getEstado());
////        enderecoDaBase.setPacientes(endereco.getPacientes());
//        enderecoService.salvar(enderecoDaBase);
//    }
//
}

