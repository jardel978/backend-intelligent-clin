package br.com.intelligentclin.service;

import br.com.intelligentclin.dtos.converters.UsuarioModelMapperConverter;
import br.com.intelligentclin.dtos.usuarioDTO.UsuarioModelDTO;
import br.com.intelligentclin.dtos.usuarioDTO.UsuarioSummaryDTO;
import br.com.intelligentclin.entity.Usuario;
import br.com.intelligentclin.entity.enums.Cargo;
import br.com.intelligentclin.repository.IUsuarioRepository;
import br.com.intelligentclin.repository.PessoaCustomRepository;
import br.com.intelligentclin.security.JWTFilterAutenticacao;
import br.com.intelligentclin.security.data.UsuarioDetailsData;
import br.com.intelligentclin.service.exception.DadoInexistenteException;
import br.com.intelligentclin.service.exception.EntidadeRelacionadaException;
import br.com.intelligentclin.service.exception.ParametroRequeridoException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.intelligentclin.security.JWTFilterAutenticacao.APLICATION_JSON_VALUE;
import static br.com.intelligentclin.security.JWTFilterAutenticacao.TOKEN_EXPIRE_AT;
import static br.com.intelligentclin.security.JWTFilterValidacao.ATRIBUTO_PREFIXO;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioModelMapperConverter usuarioConverter;

    @Autowired
    private PessoaCustomRepository<Usuario> usuarioModelCustomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioModelDTO salvar(UsuarioModelDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.mapModelDTOToEntity(usuarioDTO, Usuario.class);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
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
            throw new EntidadeRelacionadaException("Não é possível excluir o usuário: " + usuario.getNome() + " " +
                    usuario.getSobrenome() + " pois está vinculado a uma ou mais consultas.");
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
        usuarioDaBase.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuarioDaBase.setCargo(usuarioDTO.getCargo());
        usuarioRepository.save(usuarioDaBase);
    }

    public Boolean validarSenha(String email, String senha) throws ParametroRequeridoException {
        Optional<Usuario> usuario = null;
        if (email == null || senha == null)
            throw new ParametroRequeridoException("É necessário informar um endereço de email e uma senha " +
                    "para validar o usuário.");

        usuario = usuarioRepository.findByEmail(email);
        if (usuario.isEmpty())
            return false;

        return passwordEncoder.matches(senha, usuario.get().getSenha());
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(ATRIBUTO_PREFIXO)) {
            try {
                String refresh_token = authorizationHeader.substring(ATRIBUTO_PREFIXO.length());
                Algorithm algorithm = Algorithm.HMAC256(JWTFilterAutenticacao.TOKEN_SENHA);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String usuarioEmail = decodedJWT.getSubject();
                Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioEmail);
                UsuarioDetailsData usuarioDetailsData = new UsuarioDetailsData(usuario);
                String access_token = JWT.create()
                        .withSubject(usuarioDetailsData.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_AT))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("permissions", usuarioDetailsData.getAuthorities()
                                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                        )
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                //response.sendError(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else
            throw new RuntimeException("É necessário informar um Refresh token para solicitar a atualização do token.");
    }
}
