//package br.com.inteligentclin.data;
//
//import br.com.inteligentclin.entity.Usuario;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Optional;
//
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class UsuarioDetailsData implements UserDetails {
//
//    private Optional<Usuario> usuario;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return new ArrayList<>();
//    }
//
//    @Override
//    public String getPassword() {
//        return usuario.orElse(new Usuario()).getSenha();
//    }
//
//    @Override
//    public String getUsername() {
//        return usuario.orElse(new Usuario()).getLogin();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
