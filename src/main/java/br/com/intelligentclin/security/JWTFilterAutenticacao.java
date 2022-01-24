package br.com.intelligentclin.security;

import br.com.intelligentclin.entity.Usuario;
import br.com.intelligentclin.security.data.UsuarioDetailsData;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class JWTFilterAutenticacao extends UsernamePasswordAuthenticationFilter {

//    public static final int TOKEN_EXPIRE_AT = 1000 * 30; //30 s (teste)
//    public static final int REFRESH_TOKEN_EXPIRE_AT = 1000 * 60 * 20; //20 min (teste)
    public static final int TOKEN_EXPIRE_AT = 1000 * 60 * 60 * 24 * 7; //1 semana
    public static final int REFRESH_TOKEN_EXPIRE_AT = 1000 * 60 * 60 * 24 * 30 * 3; //3 meses
    public static final String APLICATION_JSON_VALUE = "application/json";
    public static final String TOKEN_SENHA = "ff163f2e-9757-4571-b2bd-a4801de92012";


    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuario.getEmail(),
                    usuario.getSenha()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuário.", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        UsuarioDetailsData usuarioDetailsData = (UsuarioDetailsData) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SENHA);
        String access_token = JWT.create()
                .withSubject(usuarioDetailsData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_AT))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("permissions", usuarioDetailsData.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                )
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(usuarioDetailsData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_AT))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
