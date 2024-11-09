package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.WatchDTO;
import br.com.ecommerce.relogios.dto.WatchResponseDTO;
import br.com.ecommerce.relogios.model.Watch;
import br.com.ecommerce.relogios.repository.WatchRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WatchServiceImpl implements WatchService {

    @Inject
    WatchRepository watchRepository;

    private static final String IMAGE_UPLOAD_DIR = "src/main/resources/static/assets/watch/";

    @Transactional
    @Override
    public List<WatchResponseDTO> findAll() {
        return watchRepository
                .listAll()
                .stream()
                .map(WatchResponseDTO::valueOf).toList();
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
        watch.setImageUrl(new ArrayList<>());
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

}
