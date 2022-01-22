package br.com.intelligentclin.repository;

import br.com.intelligentclin.entity.Endereco;
import br.com.intelligentclin.entity.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEnderecoRepository extends JpaRepository<Endereco, Long> {

    Prontuario findByCepContains(String cep);


    Endereco findByRuaAndNumeroAndBairroIgnoreCase(String rua,
                                         String numero,
                                         String bairro);


    @Query(value = "SELECT e FROM Endereco e WHERE e.pacientes IS EMPTY")
    List<Endereco> findByListPacientesIsEmpty();

}
