package br.com.intelligentclin.config;

import br.com.intelligentclin.security.JWTFilterAutenticacao;
import br.com.intelligentclin.security.JWTFilterValidacao;
import br.com.intelligentclin.security.VariavelTokenAssinatura;
import br.com.intelligentclin.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VariavelTokenAssinatura variavelTokenAssinatura;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/usuarios/token/refresh/**", "/usuarios/me/**").permitAll();
        // permissões para solicitações tipo GET
        http.authorizeRequests().antMatchers(//permitido a estagiário
                GET, "/pacientes/permitAll/**", "/enderecos/permitAll/**", "/dentistas/permitAll/**", "/prontuarios/permitAll/**", "/consultas/permitAll/**",
                "/usuarios/permitAll/**", "/files/permitAll/**"
        ).hasAnyAuthority("ROLE_USER3");
        http.authorizeRequests().antMatchers(//permitidos a diretor, gerente, atendente
                GET, "/pacientes/**", "/enderecos/**", "/dentistas/**", "/prontuarios/**", "/consultas/**",
                "/files/**"
        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER1", "ROLE_USER2");
        http.authorizeRequests().antMatchers(//permitido a diretor e gerente
                GET, "/usuarios/**"
        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER1");

        // permissões para solicitações tipo POST
        http.authorizeRequests().antMatchers(//permitidos a estagiário
                POST, "/pacientes/permitAll/**", "/enderecos/permitAll/**", "/prontuarios/permitAll/**", "/consultas/permitAll/**",
                "/files/permitAll/**"
        ).hasAnyAuthority("ROLE_USER3");
        http.authorizeRequests().antMatchers(//permitidos a diretor gerente e atendente
                POST, "/pacientes/**", "/enderecos/**", "/prontuarios/**", "/consultas/**", "/files/**"
        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER1", "ROLE_USER2");
        http.authorizeRequests().antMatchers(//permitidos a diretor e gerente
                POST, "/dentistas/**", "/usuarios/**"
        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER1");

        // permissões para solicitações tipo PUT
        http.authorizeRequests().antMatchers(
                PUT, "/usuarios/permitAll/**").hasAnyAuthority("ROLE_USER3");
        http.authorizeRequests().antMatchers(// permitidos a diretor gerente e atendente
                PUT, "/pacientes/**", "/enderecos/**", "/prontuarios/**", "/consultas/**", "/files/**"
        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER1", "ROLE_USER2");
        //estagiários não estão permitidos a autualizar registros
        http.authorizeRequests().antMatchers(//permitidos a diretor e gerente
                PUT, "/dentistas/**", "/usuarios/**"
        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER1");

        // permissões para solicitações tipo DELETE
        http.authorizeRequests().antMatchers(// permitidos a diretor gerente e atendente
                DELETE, "/pacientes/**", "/enderecos/**", "/prontuarios/**", "/consultas/**", "/files/**"
        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER1", "ROLE_USER2");
        //estagiários não estão permitidos a deletar registros
        http.authorizeRequests().antMatchers(//permitidos a diretor e gerente
                DELETE, "/dentistas/**", "/usuarios/**"
        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER1");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTFilterAutenticacao(authenticationManagerBean(), variavelTokenAssinatura));
        http.addFilterBefore(new JWTFilterValidacao(), UsernamePasswordAuthenticationFilter.class);
    }


//    @Bean
//    CorsConfigurationSource corsConfiguration() {
//        final UrlBasedCorsConfigurationSource urlBasedCorsSource = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        urlBasedCorsSource.registerCorsConfiguration("/**", corsConfiguration);
//        return urlBasedCorsSource;
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
