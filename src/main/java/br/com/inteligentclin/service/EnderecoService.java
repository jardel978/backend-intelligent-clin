package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.converters.EnderecoModelMapperConverter;
import br.com.inteligentclin.dtos.enderecoDTO.EnderecoModelDTO;
import br.com.inteligentclin.entity.Endereco;
import br.com.inteligentclin.repository.IEnderecoRepository;
import br.com.inteligentclin.service.exception.DadoExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private IEnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoModelMapperConverter enderecoConverter;


    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Optional<EnderecoModelDTO> buscarPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() ->
                new DadoExistenteException("Endereço não encontrado."));

        return Optional.ofNullable(enderecoConverter.mapEntityToModelDTO(endereco, EnderecoModelDTO.class));
    }

    public List<Endereco> buscarTodos() {
        return enderecoRepository.findAll();
    }

    public void excluirPorId(Long id) {
        try {
            enderecoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um endereço que está vinculado a um " +
                    "paciente.");
        }
    }
}
