package br.com.intelligentclin.security.data;

import br.com.intelligentclin.entity.Usuario;
import br.com.intelligentclin.entity.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDetailsData implements UserDetails {

    private Optional<Usuario> usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityUser = new ArrayList<>();
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ROLE_ADMIN");
        SimpleGrantedAuthority user1 = new SimpleGrantedAuthority("ROLE_USER1");
        SimpleGrantedAuthority user2 = new SimpleGrantedAuthority("ROLE_USER2");
        SimpleGrantedAuthority user3 = new SimpleGrantedAuthority("ROLE_USER3");
        SimpleGrantedAuthority viewer = new SimpleGrantedAuthority("ROLE_USER_VIEWER");
        if (usuario.isPresent()) {
            if (usuario.get().getCargo() == Cargo.DIRETOR) {
                authorityUser.add(admin);
                authorityUser.add(user1);
                authorityUser.add(user2);
                authorityUser.add(user3);
            }
            if (usuario.get().getCargo() == Cargo.GERENTE) {
                authorityUser.add(user1);
                authorityUser.add(user2);
                authorityUser.add(user3);
            }
            if (usuario.get().getCargo() == Cargo.ATENDENTE) {
                authorityUser.add(user2);
                authorityUser.add(user3);
            }
            if (usuario.get().getCargo() == Cargo.ESTAGIARIO) {
                authorityUser.add(user3);
            }
            // a authority de viewer será para o usuário conectar com a aplicação e apenas ver como funciona internamente
            if (usuario.get().getCargo() == Cargo.VIEWER) {
                authorityUser.add(viewer);
            }
        }
        return authorityUser;
    }

    @Override
    public String getPassword() {
        return usuario.orElse(new Usuario()).getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new Usuario()).getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Optional<Usuario> getUsuario() {
        return usuario;
    }
}
