//package br.com.inteligentclin.config;
//
//import br.com.inteligentclin.security.JWTFilterAutenticacao;
//import br.com.inteligentclin.security.JWTFilterValidacao;
//import br.com.inteligentclin.service.UsuarioDetailsService;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@EnableWebSecurity
//public class JWTConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UsuarioDetailsService usuarioDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(usuarioDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new JWTFilterAutenticacao(authenticationManager()))
//                .addFilter(new JWTFilterValidacao(authenticationManager()))
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfiguration() {
//        final UrlBasedCorsConfigurationSource urlBasedCorsSource = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        urlBasedCorsSource.registerCorsConfiguration("/**", corsConfiguration);
//        return urlBasedCorsSource;
//    }
//}
