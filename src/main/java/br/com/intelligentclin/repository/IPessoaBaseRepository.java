package br.com.intelligentclin.repository;

import br.com.intelligentclin.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IPessoaBaseRepository<T extends Pessoa> extends JpaRepository<T, Long> {

    List<T> findByEmailContains(String email);

}
