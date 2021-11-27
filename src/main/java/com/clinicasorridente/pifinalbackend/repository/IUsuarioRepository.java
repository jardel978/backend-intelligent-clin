package com.clinicasorridente.pifinalbackend.repository;

import com.clinicasorridente.pifinalbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {



}
