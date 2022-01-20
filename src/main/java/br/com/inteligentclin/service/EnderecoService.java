package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.converters.EnderecoModelMapperConverter;
import br.com.inteligentclin.dtos.enderecoDTO.EnderecoModelDTO;
import br.com.inteligentclin.dtos.enderecoDTO.EnderecoSummaryDTO;
import br.com.inteligentclin.entity.Endereco;
import br.com.inteligentclin.repository.IEnderecoRepository;
import br.com.inteligentclin.service.exception.DadoInexistenteException;
import br.com.inteligentclin.service.exception.EntidadeRelacionadaException;
import br.com.inteligentclin.service.utils.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private IEnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoModelMapperConverter enderecoConverter;

    @Autowired
    private UtilDate utilDate;


    public EnderecoModelDTO salvar(EnderecoModelDTO enderecoDTO) {
        Endereco endereco = enderecoConverter.mapModelDTOToEntity(enderecoDTO, Endereco.class);
        Endereco enderecoSalvo = enderecoRepository.saveAndFlush(endereco);
        return enderecoConverter.mapEntityToModelDTO(enderecoSalvo, EnderecoModelDTO.class);
    }

    public Optional<EnderecoModelDTO> buscarPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() ->
                new DadoInexistenteException("Endereço não encontrado na base de dados."));

        endereco.getPacientes().stream().forEach(paciente -> {
            paciente.setIdade(utilDate.gerarIdade(paciente.getDataNascimento(), LocalDate.now()));
        });
        return Optional.ofNullable(enderecoConverter.mapEntityToModelDTO(endereco, EnderecoModelDTO.class));
    }

    public Page<EnderecoSummaryDTO> buscarTodos(Pageable pageable) {
//        Limpeza de endereços não relacionados
        List<Endereco> enderecosSemPacientesRelacionados =
                enderecoRepository.findByListPacientesIsEmpty();

        enderecosSemPacientesRelacionados
                .forEach(endereco -> {
                    enderecoRepository.delete(endereco);
                });

        Page<Endereco> enderecosPage = enderecoRepository.findAll(pageable);
        return enderecosPage.map(endereco -> enderecoConverter.mapEntityToSummaryDTO(endereco,
                EnderecoSummaryDTO.class));
    }

    public void excluirPorId(Long id) throws EntidadeRelacionadaException {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new DadoInexistenteException("" +
                "Não foi possível localizar o endereço informado na base de dados."));
        boolean temPacinetes = !endereco.getPacientes().isEmpty();
        if (temPacinetes)
            throw new EntidadeRelacionadaException("Não é possível excluir um endereço que está vinculado a um " +
                    "ou mais pacientes.");
        enderecoRepository.deleteById(id);
    }

    public void atualizar(Long id, EnderecoModelDTO enderecoDTO) {
        EnderecoModelDTO enderecoDaBase = buscarPorId(id).orElseThrow(() -> new DadoInexistenteException(
                "Endereço não encontrado")
        );
        enderecoDaBase.setRua(enderecoDTO.getRua());
        enderecoDaBase.setNumero(enderecoDTO.getNumero());
        enderecoDaBase.setBairro(enderecoDTO.getBairro());
        enderecoDaBase.setCep(enderecoDTO.getCep());
        enderecoDaBase.setCidade(enderecoDTO.getCidade());
        enderecoDaBase.setEstado(enderecoDTO.getEstado());
        enderecoDaBase.setComplemento(enderecoDTO.getComplemento());
        enderecoDaBase.setPacientes(enderecoDTO.getPacientes());
        salvar(enderecoDaBase);
    }
}
