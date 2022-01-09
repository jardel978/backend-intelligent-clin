package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Endereco;
import br.com.inteligentclin.entity.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnderecoRepository extends JpaRepository<Endereco, Long> {

    Prontuario findByCepContains(String cep);

}
