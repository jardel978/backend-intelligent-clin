package br.com.intelligentclin.service;

import br.com.intelligentclin.entity.File;
import br.com.intelligentclin.entity.Prontuario;
import br.com.intelligentclin.repository.IFileRepository;
import br.com.intelligentclin.repository.IProntuarioRepository;
import br.com.intelligentclin.service.exception.DadoInexistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private IProntuarioRepository prontuarioRepository;

    @Autowired
    private IFileRepository fileRepository;

    public File salvar(MultipartFile file, Long idProntuario) {
        try {
            return carregarArquivo(file, idProntuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page<File> buscarTodos(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }

    public File atualizar(Long id, MultipartFile file) throws IOException {
        File arquivoDaBase = fileRepository.findById(id).orElseThrow(() -> new DadoInexistenteException(
                "Arquivo não encontrado."));
        try {
            arquivoDaBase.setNome(file.getOriginalFilename());
            arquivoDaBase.setData(file.getBytes());
            arquivoDaBase.setTipo(file.getContentType());
            return fileRepository.saveAndFlush(arquivoDaBase);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private File carregarArquivo(MultipartFile file, Long idProntuario) throws IOException {
        byte[] data = file.getBytes();
        String tipo = file.getContentType();
        String nomeArquivo = file.getOriginalFilename();
        validarNomeArquivo(nomeArquivo);

        Prontuario prontuario = prontuarioRepository.findById(idProntuario).orElseThrow(() ->
                new DadoInexistenteException("Prontuário não encontrado."));


        File arquivo = fileRepository.saveAndFlush(
                File.builder()
                        .data(data)
                        .nome(nomeArquivo)
                        .tipo(tipo)
                        .prontuario(prontuario).build()
        );
        prontuario.setFile(arquivo);
        prontuarioRepository.save(prontuario);
        return arquivo;
    }

    private void validarNomeArquivo(String nomeArquivo) {
        if (nomeArquivo.contains("..")) {
            throw new RuntimeException("O nome do arquivo ( " + nomeArquivo + " ) é inválido.");
        }
    }

}
