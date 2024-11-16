package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.model.Storage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class StorageServiceImp implements StorageService {

    @Inject
    FileService fileService;

    @Override
    public List<Storage> findAll() {
        return List.of();
    }

    @Override
    public Storage findById(Long id) {
        return null;
    }

    @Override
    public Storage create(Storage dto, InputStream imagem) throws IOException {
        try {
            File file =  fileService.save(dto.getId(), dto.getName(), imagem);
            dto.setUrl(file.getAbsolutePath());
            return dto;
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(Long id, Storage dto) {

    }

    @Override
    public void delete(Long id) {

    }
}
