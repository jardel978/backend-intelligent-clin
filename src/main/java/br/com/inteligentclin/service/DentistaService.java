package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.dentistaDTO.DentistaModelDTO;
import br.com.inteligentclin.dtos.dentistaDTO.DentistaSummaryDTO;
import br.com.inteligentclin.dtos.converters.DentistaModelMapperConverter;
import br.com.inteligentclin.entity.Dentista;
import br.com.inteligentclin.entity.enums.Especialidade;
import br.com.inteligentclin.repository.IDentistaRepository;
import br.com.inteligentclin.repository.PessoaCustomRepository;
import br.com.inteligentclin.service.exception.DadoExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DentistaService {

    @Autowired
    private IDentistaRepository dentistaRepository;

    @Autowired
    private PessoaCustomRepository<Dentista, DentistaModelDTO> dentistaModelCustomRepository;

    @Autowired
    private DentistaModelMapperConverter dentistaConverter;

    public DentistaModelDTO salvar(DentistaModelDTO dentistaDTO) {
        Dentista dentista = dentistaConverter.mapDentistaModelDTOParaDentista(dentistaDTO);
        dentistaRepository.save(dentista);

        return dentistaDTO;
    }

    public Optional<DentistaModelDTO> buscarPorId(Long id) {
        Dentista dentista = dentistaRepository.findById(id).orElseThrow(() ->
                new DadoExistenteException("Dentista não encontrado.")
        );

        return Optional.ofNullable(dentistaConverter.mapDentistaParaDentistaModelDTO(dentista));
    }


    public Page<DentistaModelDTO> buscarCustomizado(Pageable pageable, Long id, String nome, String sobrenome,
                                                    String cpf) {
        return dentistaModelCustomRepository.find(pageable, id, nome, sobrenome, cpf,
                Dentista.class);
    }

    public DentistaModelDTO buscarPorMatricula(String numMatricula) {
        Dentista dentista = dentistaRepository.findByMatriculaContains(numMatricula).orElseThrow(() ->
                new DadoExistenteException("Não localizamos um Dentista com a série de matrícula informada.")
        );

        return dentistaConverter.mapDentistaParaDentistaModelDTO(dentista);
    }

    public Page<DentistaSummaryDTO> buscarPorEspecialidades(Pageable pageable, Especialidade nomeEspecialidade) {
        Page<Dentista> dentistasPage = dentistaRepository.findByEspecialidadesContains(pageable, nomeEspecialidade);
        return dentistaConverter.convertPageDentistasParaDentistasSummaryDTO(dentistasPage, pageable);
    }

    public Page<DentistaSummaryDTO> buscarTodos(Pageable pageable) {
        Page<Dentista> dentistasPage = dentistaRepository.findAll(pageable);
        return dentistaConverter.convertPageDentistasParaDentistasSummaryDTO(dentistasPage, pageable);
    }

    public void excluirPorId(Long id) {
        try {
            dentistaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um dentista que está vinculado a uma " +
                    "consulta.");
        }
    }

    public void atualizar(Long id, DentistaModelDTO dentistaDTO) {
        DentistaModelDTO dentistaDaBase =
                buscarPorId(id).orElseThrow(() ->
                        new DadoExistenteException("Dentista não encontrado.")
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
}
