package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.StorageDTO;
import br.com.ecommerce.relogios.dto.WatchDTO;
import br.com.ecommerce.relogios.dto.WatchResponseDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface WatchService {

    public List<WatchResponseDTO> findAll();

    public WatchResponseDTO findWatchById(Long id);

    public WatchResponseDTO create(WatchDTO watchDTO);

    public void update(Long id, WatchDTO watchDTO);

    public void delete(Long id);

    public void uploadImagePerfil(Long idWatch, StorageDTO imagePerfil, InputStream inputStream) throws IOException;
}
