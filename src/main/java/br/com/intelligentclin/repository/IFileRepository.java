package br.com.intelligentclin.repository;

import br.com.intelligentclin.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFileRepository extends JpaRepository<File, Long> {

}
