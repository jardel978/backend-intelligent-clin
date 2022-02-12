//package br.com.intelligentclin.config;
//
//import br.com.intelligentclin.entity.Usuario;
//import br.com.intelligentclin.entity.enums.Cargo;
//import br.com.intelligentclin.repository.IUsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class IniciarUsuarioAdmin implements CommandLineRunner {
//
//    @Autowired
//    private IUsuarioRepository usuarioRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Usuario usuarioRoot = new Usuario();
//        usuarioRoot.setNome("admin");
//        usuarioRoot.setCpf("000.000.000-00");
//        usuarioRoot.setEmail("admin@intelligentclin.com");
//        usuarioRoot.setTelefone("00 000000000");
//        usuarioRoot.setSenha(passwordEncoder.encode("7890123"));
//        usuarioRoot.setCargo(Cargo.DIRETOR);
//
//        usuarioRepository.save(usuarioRoot);
//    }
//}
