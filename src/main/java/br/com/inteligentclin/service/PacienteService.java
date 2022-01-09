package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.converters.PacienteModelMapperConverter;
import br.com.inteligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.inteligentclin.entity.Idade;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.repository.IEnderecoRepository;
import br.com.inteligentclin.repository.IPacienteRepository;
import br.com.inteligentclin.repository.PessoaCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.UnexpectedTypeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IEnderecoRepository enderecoRepository;

    @Autowired
    private PacienteModelMapperConverter pacienteConverter;

    @Autowired
    private PessoaCustomRepository<Paciente, PacienteModelDTO> pacienteModelCustomRepository;

    public PacienteModelDTO salvar(PacienteModelDTO pacienteDTO) {
        Paciente paciente = pacienteConverter.mapPacienteModelDTOParaPaciente(pacienteDTO);

        pacienteRepository.save(paciente);

        paciente.setIdade(gerarIdade(Period.between(paciente.getDataNascimento(), LocalDate.now())));
        return pacienteConverter.mapPacienteParaPacienteModelDTO(paciente);
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    public void excluirPorId(Long id) {
        try {
            pacienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um paciente que está vinculado a uma " +
                    "consulta.");
        }
    }

    public static Idade gerarIdade(Period period) {
        return Idade.builder()
                .dias(period.getDays())
                .meses(period.getMonths())
                .anos(period.getYears()).build();
    }
}

