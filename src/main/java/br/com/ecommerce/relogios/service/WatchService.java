package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.WatchDTO;
import br.com.ecommerce.relogios.dto.WatchResponseDTO;

import java.util.List;

public interface WatchService {

    public List<WatchResponseDTO> findAll();

    public WatchResponseDTO findWatchById(Long id);

    public WatchResponseDTO create(WatchDTO watchDTO);

    public void update(Long id, WatchDTO watchDTO);

    public void delete(Long id);
}
