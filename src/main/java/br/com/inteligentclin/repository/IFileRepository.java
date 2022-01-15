package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFileRepository extends JpaRepository<File, Long> {

}
