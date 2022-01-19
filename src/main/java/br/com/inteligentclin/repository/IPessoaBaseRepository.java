package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IPessoaBaseRepository<T extends Pessoa> extends JpaRepository<T, Long> {

//    List<T> findByNomeContains(String nome);
//    List<T> findBySobrenomeContains(String sobrenome);
//    T findByCpf(String cpf);
    List<T> findByEmailContains(String email);

}
