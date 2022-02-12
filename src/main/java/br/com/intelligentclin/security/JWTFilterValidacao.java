package br.com.intelligentclin.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static br.com.intelligentclin.security.JWTFilterAutenticacao.APLICATION_JSON_VALUE;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JWTFilterValidacao extends OncePerRequestFilter {

    public static final String ATRIBUTO_PREFIXO = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        if (request.getServletPath().equals("/login")
                || request.getServletPath().equals("/usuarios/token/refresh")
                || request.getServletPath().equals("/usuarios/me")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(ATRIBUTO_PREFIXO)) {
                try {
                    String token = authorizationHeader.substring(ATRIBUTO_PREFIXO.length());
                    Algorithm algorithm = Algorithm.HMAC256(JWTFilterAutenticacao.TOKEN_SENHA);
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(token);
                    String usuarioEmail = decodedJWT.getSubject();
                    String[] permissions = decodedJWT.getClaim("permissions").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(permissions).forEach(permission -> authorities.add(new SimpleGrantedAuthority((permission))));
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(usuarioEmail, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    //response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    Map<String, Object> data = new HashMap<>();
                    data.put("exception", e.getMessage());
                    if (e.getMessage().contains("Token has expired"))
                        data.put("message", "token expirado");
                    else
                        data.put("message", "acesso negado");
                    data.put("timestamp", new Date());
                    response.setContentType(APLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), data);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
