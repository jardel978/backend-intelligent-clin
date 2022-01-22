//package br.com.inteligentclin.security;
//
//import br.com.inteligentclin.data.UsuarioDetailsData;
//import br.com.inteligentclin.entity.Usuario;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//@NoArgsConstructor
//@AllArgsConstructor
//public class JWTFilterAutenticacao extends UsernamePasswordAuthenticationFilter {
//
//    //    public static final int TOKEN_EXPIRE_AT = 1000 * 60 * 60 * 24 * 7; //1 semana
//    public static final int TOKEN_EXPIRE_AT = 1000 * 60; //1 min
//    public static final String TOKEN_SENHA = "ff163f2e-9757-4571-b2bd-a4801de92012";
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request,
//                                                HttpServletResponse response) throws AuthenticationException {
//        try {
//            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
//            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    usuario.getLogin(),
//                    usuario.getSenha(),
//                    new ArrayList<>()
//            ));
//        } catch (IOException e) {
//            throw new RuntimeException("Falha ao autenticar usuário.", e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        UsuarioDetailsData usuarioDetailsData = (UsuarioDetailsData) authResult.getPrincipal();
//        String token = JWT.create()
//                .withSubject(usuarioDetailsData.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_AT))
//                .sign(Algorithm.HMAC512(TOKEN_SENHA));
//
//        response.getWriter().write(token);
//        response.getWriter().flush();
//    }
//}
