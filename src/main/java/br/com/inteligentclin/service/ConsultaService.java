package br.com.inteligentclin.service;

import br.com.inteligentclin.repository.IConsultaRepository;
import br.com.inteligentclin.entity.Consulta;
import br.com.inteligentclin.entity.Dentista;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.entity.Usuario;
import br.com.inteligentclin.repository.IDentistaRepository;
import br.com.inteligentclin.repository.IPacienteRepository;
import br.com.inteligentclin.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IDentistaRepository dentistaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public Consulta salvar(Consulta consulta) {
        Paciente paciente = pacienteRepository.findById(consulta.getPaciente().getId()).isPresent()
                ? pacienteRepository.getById(consulta.getPaciente().getId()) : null;
        Dentista dentista = dentistaRepository.findById(consulta.getDentista().getId()).isPresent()
                ? dentistaRepository.getById(consulta.getDentista().getId()) : null;
        Usuario usuario = usuarioRepository.findById(consulta.getUsuario().getId()).isPresent()
                ? usuarioRepository.getById(consulta.getUsuario().getId()) : null;

        if (paciente != null && dentista != null && usuario != null) {
            Consulta consulta1 = Consulta.builder()
                    .paciente(paciente)
                    .dentista(dentista)
                    .usuario(usuario)
                    .dataConsulta(consulta.getDataConsulta())
                    .horaConsulta(consulta.getHoraConsulta()).build();
            return consultaRepository.save(consulta1);
        } else
            throw new RuntimeException("Certifique-se de que não existem campos vazios.");
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public List<Consulta> buscarTodos() {
        return consultaRepository.findAll();
    }

    public void excluirPorId(Long id) {
        consultaRepository.deleteById(id);
    }
}
