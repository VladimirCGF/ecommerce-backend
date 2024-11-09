package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.StockDTO;
import br.com.ecommerce.relogios.dto.StockResponseDTO;

import java.util.List;

public interface StockService {

    public List<StockResponseDTO> findAll();

    public StockResponseDTO findStockById(Long id);

    public StockResponseDTO create(StockDTO stockDTO);

    public void update(Long id, StockDTO stockDTO);

    public void delete(Long id);
}
