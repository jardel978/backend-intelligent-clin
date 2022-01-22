package br.com.intelligentclin.service;

import br.com.intelligentclin.dtos.converters.DentistaModelMapperConverter;
import br.com.intelligentclin.dtos.dentistaDTO.DentistaModelDTO;
import br.com.intelligentclin.dtos.dentistaDTO.DentistaSummaryDTO;
import br.com.intelligentclin.entity.Dentista;
import br.com.intelligentclin.entity.enums.Especialidade;
import br.com.intelligentclin.repository.IDentistaRepository;
import br.com.intelligentclin.repository.PessoaCustomRepository;
import br.com.intelligentclin.service.exception.DadoInexistenteException;
import br.com.intelligentclin.service.exception.EntidadeRelacionadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaService {

    @Autowired
    private IDentistaRepository dentistaRepository;

    @Autowired
    private PessoaCustomRepository<Dentista> dentistaModelCustomRepository;

    @Autowired
    private DentistaModelMapperConverter dentistaConverter;

    public DentistaModelDTO salvar(DentistaModelDTO dentistaDTO) {
        Dentista dentista = dentistaConverter.mapModelDTOToEntity(dentistaDTO, Dentista.class);
        Dentista dentistaSalvo = dentistaRepository.saveAndFlush(dentista);
        return dentistaConverter.mapEntityToModelDTO(dentistaSalvo, DentistaModelDTO.class);
    }

    public Optional<DentistaModelDTO> buscarPorId(Long id) {
        Dentista dentista = dentistaRepository.findById(id).orElseThrow(() ->
                new DadoInexistenteException("Dentista não encontrado.")
        );

        return Optional.ofNullable(dentistaConverter.mapEntityToModelDTO(dentista, DentistaModelDTO.class));
    }


    public Page<DentistaModelDTO> buscarCustomizado(Pageable pageable,
                                                    Long id,
                                                    String nome,
                                                    String sobrenome,
                                                    String cpf) {
        List<Dentista> lista = dentistaModelCustomRepository.find(id, nome, sobrenome, cpf, Dentista.class);
        Page<Dentista> pageDentistas = new PageImpl<>(lista, pageable, lista.stream().count());

        return pageDentistas.map(dentista -> dentistaConverter.mapEntityToModelDTO(dentista, DentistaModelDTO.class));
    }

    public DentistaModelDTO buscarPorMatricula(String numMatricula) {
        Dentista dentista = dentistaRepository.findByMatriculaIgnoreCaseContains(numMatricula).orElseThrow(() ->
                new DadoInexistenteException("Não localizamos um Dentista com a série de matrícula informada.")
        );

        return dentistaConverter.mapEntityToModelDTO(dentista, DentistaModelDTO.class);
    }

    public Page<DentistaSummaryDTO> buscarPorEspecialidade(Pageable pageable, Especialidade nomeEspecialidade) {
        Page<Dentista> dentistasPage = dentistaRepository.findByEspecialidadesContains(pageable, nomeEspecialidade);
        return dentistasPage.map(dentista -> {
            DentistaSummaryDTO dentistaDTO = dentistaConverter.mapEntityToSummaryDTO(dentista,
                    DentistaSummaryDTO.class);
            dentistaDTO.setTemConsultas(verirficarSeDentistaTemConsultas(dentista));
            return dentistaDTO;
        });
    }

    public Page<DentistaSummaryDTO> buscarTodos(Pageable pageable) {
        Page<Dentista> dentistasPage = dentistaRepository.findAll(pageable);
        return dentistasPage.map(dentista -> {
            DentistaSummaryDTO dentistaDTO = dentistaConverter.mapEntityToSummaryDTO(dentista,
                    DentistaSummaryDTO.class);
            dentistaDTO.setTemConsultas(verirficarSeDentistaTemConsultas(dentista));
            return dentistaDTO;
        });
    }

    public void excluirPorId(Long id) throws EntidadeRelacionadaException {
        Dentista dentista = dentistaRepository.findById(id).orElseThrow(() ->
                new DadoInexistenteException("Dentista não encontrado."));

        boolean temConsultas = !dentista.getConsultas().isEmpty();
        boolean temProntuarios = !dentista.getProntuarios().isEmpty();
        if (temProntuarios && temConsultas)
            throw new EntidadeRelacionadaException("O(A) dentista " + dentista.getNome() + " " + dentista.getSobrenome()
                    + " possui consultas e prontuários sob sua responsabilidade. Não é possível excluí-lo.");
        if (temConsultas)
            throw new EntidadeRelacionadaException("Não é possível excluir o(a) dentista: " + dentista.getNome() + " " + dentista.getSobrenome()
                    + " pois está vinculado a uma ou mais consultas.");
        if (temProntuarios)
            throw new EntidadeRelacionadaException("Não é possível excluir o(a) dentista: " + dentista.getNome() + " " + dentista.getSobrenome()
                    + " pois está vinculado a um ou mais prontuários.");
        dentistaRepository.deleteById(dentista.getId());
    }

    public void atualizar(Long id, DentistaModelDTO dentistaDTO) {
        DentistaModelDTO dentistaDaBase =
                buscarPorId(id).orElseThrow(() ->
                        new DadoInexistenteException("Dentista não encontrado.")
                );
        dentistaDaBase.setNome(dentistaDTO.getNome());
        dentistaDaBase.setSobrenome(dentistaDTO.getSobrenome());
        dentistaDaBase.setCpf(dentistaDTO.getCpf());
        dentistaDaBase.setEmail(dentistaDTO.getEmail());
        dentistaDaBase.setTelefone(dentistaDTO.getTelefone());
        dentistaDaBase.setMatricula(dentistaDTO.getMatricula());
        dentistaDaBase.setEspecialidades(dentistaDTO.getEspecialidades());
        dentistaDaBase.setConsultas(dentistaDTO.getConsultas());
        dentistaDaBase.setProntuarios(dentistaDTO.getProntuarios());
        salvar(dentistaDaBase);
    }

    public Boolean verirficarSeDentistaTemConsultas(Dentista dentista) {
        return !dentista.getConsultas().isEmpty();
    }
}
