package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Endereco;
import br.com.inteligentclin.entity.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEnderecoRepository extends JpaRepository<Endereco, Long> {

    Prontuario findByCepContains(String cep);

//    @Query("from Endereco where rua = :rua and numero = :numero and bairro = :bairro")
//    Endereco findByRuaAndNumeroAndBairro(@Param("rua") String rua,
//                                          @Param("numero") String numero,
//                                          @Param("bairro") String bairro);

    Endereco findByRuaAndNumeroAndBairro(String rua,
                                         String numero,
                                         String bairro);

//    @Query("from Endereco where count(*) Endereco.pacientes = :quantidade")
//    List<Endereco> listarEnderecosNaoRelacionados(@Param("quantidade") int quantidade);

    @Query(value = "SELECT e FROM Endereco e WHERE e.pacientes IS EMPTY")
    List<Endereco> findByListPacientesIsEmpty();

}
