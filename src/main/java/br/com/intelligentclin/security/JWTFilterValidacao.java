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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
                || request.getServletPath().equals("/usuarios/token/refresh")) {
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
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", e.getMessage());
                    response.setContentType(APLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }

        }
    }

//    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
//        String usuarioLogin = JWT.require(Algorithm.HMAC256(JWTFilterAutenticacao.TOKEN_SENHA))
//                .build()
//                .verify(token)
//                .getSubject();
//        List<String> aud = JWT.require(Algorithm.HMAC256(JWTFilterAutenticacao.TOKEN_SENHA))
//                .build()
//                .verify(token)
//                .getClaim("permissions").asList(String.class);
//
//        if (usuarioLogin == null)
//            return null;
////        List<GrantedAuthority> authorityUser = AuthorityUtils.createAuthorityList(aud);
//        return new UsernamePasswordAuthenticationToken(
//                usuarioLogin,
//                null,
//                null);
//    }
}
