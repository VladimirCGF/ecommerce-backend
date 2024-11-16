package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.StorageDTO;
import br.com.ecommerce.relogios.dto.StorageResponseDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface StorageService {

    public List<StorageResponseDTO> findAll();

    public StorageResponseDTO findById(Long id);

    public StorageResponseDTO create(StorageDTO storageDTO, InputStream imagem) throws IOException;

    public void update(Long id, StorageDTO storageDTO, InputStream imagem) throws IOException;

    public void delete(Long id);

    public List<StorageResponseDTO> findAllByWatchId(Long id);

    public long count();

    public long countByNome(String name);
}
