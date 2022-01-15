package br.com.inteligentclin.controller;

import br.com.inteligentclin.entity.File;
import br.com.inteligentclin.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/{idProntuario}")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
//    @PreAuthorize("hasRole('ROLE')")
    public File salvar(@RequestBody MultipartFile file, @PathVariable("idProntuario") Long idProntuario) {
        return fileService.salvar(file, idProntuario);
    }

    @GetMapping
    public Page<File> buscarTodos(Pageable pageable) {
        return fileService.buscarTodos(pageable);
    }

    @PutMapping("/{id}")
    @Transactional
    public File atualizar(@PathVariable("id") Long id, @RequestBody MultipartFile file) throws IOException {
        return fileService.atualizar(id, file);
    }

}
