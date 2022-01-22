package br.com.intelligentclin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PessoaCustomRepository<T> {

    private final EntityManager entityManager;

    @Autowired
    public PessoaCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<T> find(Long id, String nome, String sobrenome, String cpf, Class<T> clazz) {

        String query = "select T from " + clazz.getName() + " as T ";
        String condicao = "where";

        if (id != null) {
            query += condicao + " T.id = :id";
            condicao = " and";
        }

        if (nome != null) {
//            query += condicao + " T.nome ~* :nome";
            query += condicao + " UPPER(T.nome) like UPPER(:nome)";
            condicao = " and";
        }

        if (sobrenome != null) {
            query += condicao + " UPPER(T.sobrenome) like UPPER(:sobrenome)";
            condicao = " and";
        }

        if (cpf != null) {
            query += condicao + " T.cpf like :cpf";
        }

        var qry = entityManager.createQuery(query);

        if (id != null) {
           qry.setParameter("id", id);
        }

        if (nome != null) {
            qry.setParameter("nome", nome);
        }

        if (sobrenome != null) {
            qry.setParameter("sobrenome", sobrenome);
        }

        if (cpf != null) {
            qry.setParameter("cpf", cpf);
        }

        return qry.getResultList();
    }

}
