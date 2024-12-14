package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.*;
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
public class WatchServiceImpl implements WatchService {

    @Inject
    WatchRepository watchRepository;

    @Inject
    FileService fileService;

    @Inject
    StorageService storageService;

    @Inject
    StorageRepository storageRepository;

    private static final String IMAGE_UPLOAD_DIR = "src/main/resources/static/assets/watch/";

    private static final String UPLOAD_DIR = "src/main/resources/images";

    @Transactional
    @Override
    public List<WatchListResponseDTO> findAll() {
        return watchRepository
                .listAll()
                .stream()
                .map(WatchListResponseDTO::valueOf).toList();
    }

    @Override
    public WatchResponseDTO findWatchById(Long id) {
        if (watchRepository.findById(id) == null) {
            throw new NotFoundException("id watch not found");
        }
        return WatchResponseDTO.valueOf(watchRepository.findById(id));
    }

    @Transactional
    @Override
    public WatchResponseDTO create(WatchDTO watchDTO) {
        Watch watch = new Watch();
        watch.setName(watchDTO.name());
        watch.setDescription(watchDTO.description());
        watch.setPrice(watchDTO.price());
        watch.setMaterial(watchDTO.material());
        watch.setColor(watchDTO.color());
        watch.setGender(watchDTO.gender());
        watch.setBrand(watchDTO.brand());
        watch.setFormat(watchDTO.format());
        watch.setMechanism(watchDTO.mechanism());
        Storage storage = new Storage();
        watchRepository.persist(watch);
        return WatchResponseDTO.valueOf(watch);
    }

    @Transactional
    @Override
    public void update(Long id, WatchDTO watchDTO) {
        Watch watch = watchRepository.findById(id);
        if (watchRepository.findById(id) == null) {
            throw new NotFoundException("id watch not found");
        }
        watch.setName(watchDTO.name());
        watch.setDescription(watchDTO.description());
        watch.setPrice(watchDTO.price());
        watch.setMaterial(watchDTO.material());
        watch.setColor(watchDTO.color());
        watch.setGender(watchDTO.gender());
        watch.setBrand(watchDTO.brand());
        watch.setFormat(watchDTO.format());
        watch.setMechanism(watchDTO.mechanism());
        watchRepository.persist(watch);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (watchRepository.findById(id) == null) {
            throw new NotFoundException("id watch not found");
        }
        watchRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void uploadImagePerfil(Long idWatch, StorageDTO imagePerfil, InputStream inputStream) throws IOException {
        Watch watch = watchRepository.findById(idWatch);
        if (watch == null) {
            throw new NotFoundException("id watch not found");
        }
        StorageResponseDTO storageResponseDTO =  storageService.create(imagePerfil, inputStream);
        if (storageResponseDTO == null) {
            throw new NotFoundException("id storage not found");
        }
        Storage storage = new Storage();
        storage.setWatch(watch);
        storage.setName(storageResponseDTO.name());
        storage.setUrl(storageResponseDTO.url());
        storage.setId(storageResponseDTO.id());
        watch.setImagePerfil(storage);
        watchRepository.persist(watch);
    }

//    @Transactional
//    @Override
//    public List<OrderItemListResponseDTO> getWatchesByOrderId(Long orderId) {
//        return watchRepository
//                .findWatchesByOrderId(orderId)
//                .stream()
//                .map(OrderItemListResponseDTO::valueOf).toList();
//
//    }

    @Transactional
    @Override
    public List<WatchResponseDTO> findByName(String name) {
        return watchRepository
                .findByName(name)
                .stream()
                .map(WatchResponseDTO::valueOf).toList();
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
