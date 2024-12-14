package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.*;
import br.com.ecommerce.relogios.model.Watch;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface WatchService {

    public List<WatchListResponseDTO> findAll();

    public WatchResponseDTO findWatchById(Long id);

    public WatchResponseDTO create(WatchDTO watchDTO);

    public void update(Long id, WatchDTO watchDTO);

    public void delete(Long id);

    public void uploadImagePerfil(Long idWatch, StorageDTO imagePerfil, InputStream inputStream) throws IOException;


//    List<OrderItemListResponseDTO> getWatchesByOrderId(Long orderId);

    List<WatchResponseDTO> findByName(String name);
}
