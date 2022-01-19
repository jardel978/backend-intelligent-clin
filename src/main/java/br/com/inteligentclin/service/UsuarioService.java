package br.com.inteligentclin.service;

import br.com.inteligentclin.dtos.converters.UsuarioModelMapperConverter;
import br.com.inteligentclin.dtos.usuarioDTO.UsuarioModelDTO;
import br.com.inteligentclin.dtos.usuarioDTO.UsuarioSummaryDTO;
import br.com.inteligentclin.entity.Usuario;
import br.com.inteligentclin.entity.enums.Cargo;
import br.com.inteligentclin.repository.IUsuarioRepository;
import br.com.inteligentclin.repository.PessoaCustomRepository;
import br.com.inteligentclin.service.exception.DadoExistenteException;
import br.com.inteligentclin.service.exception.DadoInexistenteException;
import br.com.inteligentclin.service.exception.EntidadeRelacionadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioModelMapperConverter usuarioConverter;

    @Autowired
    private PessoaCustomRepository<Usuario> usuarioModelCustomRepository;

    public UsuarioModelDTO salvar(UsuarioModelDTO usuarioDTO) throws DadoExistenteException {
        Optional<Usuario> usuarioBase = usuarioRepository.findByLogin(usuarioDTO.getLogin());
        if (usuarioBase.isPresent())
            throw new DadoExistenteException("O login: " + usuarioBase.get().getLogin() + " já existe. Tente outro " +
                    "diferente.");
        Usuario usuario = usuarioConverter.mapModelDTOToEntity(usuarioDTO, Usuario.class);
        Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
        return usuarioConverter.mapEntityToModelDTO(usuarioSalvo, UsuarioModelDTO.class);
    }

    public Page<UsuarioModelDTO> buscarCustomizado(Pageable pageable,
                                                   Long id,
                                                   String nome,
                                                   String sobrenome,
                                                   String cpf) {
        List<Usuario> lista = usuarioModelCustomRepository.find(id, nome, sobrenome, cpf, Usuario.class);
        Page<Usuario> pageUsuarios = new PageImpl<>(lista, pageable, lista.stream().count());
        return pageUsuarios.map(usuario ->
                usuarioConverter.mapEntityToModelDTO(usuario, UsuarioModelDTO.class));
    }

    public List<UsuarioModelDTO> buscarPorEmail(String email) {
        List<Usuario> lista = usuarioRepository.findByEmailContains(email);
        if (lista.isEmpty())
            throw new DadoInexistenteException("Nenhum dentista foi encontrado com o email informado.");
        return usuarioConverter.convertListEntityToModelDTO(lista, UsuarioModelDTO.class);
    }

    public List<UsuarioModelDTO> buscarPorCargo(String cargo) {
        try {
            Cargo stringParaEnum = Cargo.valueOf(cargo.toUpperCase());
            List<Usuario> lista = usuarioRepository.findByCargo(stringParaEnum);
            if (lista.isEmpty())
                throw new DadoInexistenteException("Nenhum usuário foi encontrado para o cargo de " + cargo.toUpperCase(Locale.ROOT));
            return usuarioConverter.convertListEntityToModelDTO(lista, UsuarioModelDTO.class);
        } catch (IllegalArgumentException e) {
            throw new DadoInexistenteException("O cargo informado está inválido.");
        }
    }

    public Optional<UsuarioModelDTO> buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new DadoInexistenteException("" +
                "O usuário informado não pode ser localizado na base de dados"));

        return Optional.ofNullable(usuarioConverter.mapEntityToModelDTO(usuario, UsuarioModelDTO.class));
    }

    public Page<UsuarioSummaryDTO> buscarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuario -> usuarioConverter.mapEntityToSummaryDTO(usuario, UsuarioSummaryDTO.class));
    }

    public void excluirPorId(Long id) throws EntidadeRelacionadaException {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new DadoInexistenteException("" +
                "O usuário informado não foi localizado na base de dados."));
        boolean temConsulta = !usuario.getConsultas().isEmpty();
        if (temConsulta)
            throw new EntidadeRelacionadaException("Não é possível excluir o usuário de login: " + usuario.getLogin() +
                    " pois está vinculado a uma ou mais consultas.");
        usuarioRepository.deleteById(id);
    }

    public void atualizar(Long id, UsuarioModelDTO usuarioDTO) {

        Usuario usuarioDaBase = usuarioRepository.findById(id).orElseThrow(() ->
                new DadoInexistenteException("Usuário não encontrado na base de dados.")
        );
        usuarioDaBase.setNome(usuarioDTO.getNome());
        usuarioDaBase.setSobrenome(usuarioDTO.getSobrenome());
        usuarioDaBase.setCpf(usuarioDTO.getCpf());
        usuarioDaBase.setEmail(usuarioDTO.getEmail());
        usuarioDaBase.setTelefone(usuarioDTO.getTelefone());
        usuarioDaBase.setSenha(usuarioDTO.getSenha());
        usuarioDaBase.setCargo(usuarioDTO.getCargo());
        usuarioRepository.save(usuarioDaBase);
    }

}
