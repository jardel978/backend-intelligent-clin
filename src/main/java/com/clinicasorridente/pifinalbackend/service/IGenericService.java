package com.clinicasorridente.pifinalbackend.service;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T, K> {

    T salvar(T t);
    Optional<T> buscarPorId(K id);
    List<T> buscarTodos();
    void excluirPorId(K id);

}
