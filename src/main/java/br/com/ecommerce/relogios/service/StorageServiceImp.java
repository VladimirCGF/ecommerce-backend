package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.StorageDTO;
import br.com.ecommerce.relogios.dto.StorageResponseDTO;
import br.com.ecommerce.relogios.model.Storage;
import br.com.ecommerce.relogios.model.Watch;
import br.com.ecommerce.relogios.repository.StorageRepository;
import br.com.ecommerce.relogios.repository.WatchRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class StorageServiceImp implements StorageService {

    @Inject
    FileService fileService;

    @Inject
    WatchRepository watchRepository;

    @Inject
    StorageRepository storageRepository;

    @Override
    public List<StorageResponseDTO> findAll() {
        return storageRepository
                .listAll()
                .stream()
                .map(StorageResponseDTO::valueOf).toList();
    }

    @Override
    public StorageResponseDTO findById(Long id) {
        if (storageRepository.findById(id) == null) {
            throw new NotFoundException("id storage not found");
        }
        return StorageResponseDTO.valueOf(storageRepository.findById(id));
    }

    @Override
    public List<StorageResponseDTO> findAllByWatchId(Long id) {
        Watch watch = watchRepository.findById(id);
        if (watch == null) {
            throw new NotFoundException("id watch not found");
        }
        List<Storage> storage = storageRepository.findAllByWatchId(watch.getId());
        return storage
                .stream()
                .map(StorageResponseDTO::valueOf).toList();
    }


    @Transactional
    @Override
    public StorageResponseDTO create(StorageDTO storageDTO, InputStream imagem) throws IOException {
        Storage storage = new Storage();
        Watch watch = watchRepository.findById(storageDTO.idWatch());
        if (watch == null) {
            throw new NotFoundException("Watch not found");
        }
        String uniqueFileName = getUniqueFileName(storageDTO.name());
        storage.setWatch(watch);
        File file = fileService.save(storage.getWatch().getId(), uniqueFileName, imagem);
        storage.setName(uniqueFileName);
        storage.setUrl(file.getAbsolutePath());
        storageRepository.persist(storage);
        return StorageResponseDTO.valueOf(storage);
    }


    @Transactional
    @Override
    public void update(Long id, StorageDTO storageDTO, InputStream imagem) throws IOException {
        Storage storage = storageRepository.findById(id);
        if (storage == null) {
            throw new NotFoundException("id storage not found");
        }
        Watch watch = watchRepository.findById(storageDTO.idWatch());
        if (watch == null) {
            throw new NotFoundException("Watch not found");
        }
        storage.setWatch(watch);
        storage.setName(storageDTO.name());
        File file = fileService.save(storage.getWatch().getId(), storage.getName(), imagem);
        storage.setUrl(file.getAbsolutePath());
        storageRepository.persist(storage);
    }


    @Transactional
    @Override
    public void delete(Long id) {
        Storage storage = storageRepository.findById(id);
        if (storage == null) {
            throw new NotFoundException("id storage not found");
        }
        fileService.delete(storage.getUrl());
        storageRepository.deleteById(id);
    }

    private String getUniqueFileName(String baseName) {
        String nameWithoutExtension = baseName.substring(0, baseName.lastIndexOf('.'));
        String extension = baseName.substring(baseName.lastIndexOf('.'));

        String newName = baseName;
        int counter = 1;

        while (storageRepository.existsByName(newName)) {
            System.out.println("Checking existence of: " + newName);
            newName = nameWithoutExtension + "(" + counter + ")" + extension;
            counter++;
        }
        return newName;
    }

}
