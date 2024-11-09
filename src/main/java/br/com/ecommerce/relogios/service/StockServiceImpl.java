package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.StockDTO;
import br.com.ecommerce.relogios.dto.StockResponseDTO;
import br.com.ecommerce.relogios.model.Stock;
import br.com.ecommerce.relogios.model.Watch;
import br.com.ecommerce.relogios.repository.StockRepository;
import br.com.ecommerce.relogios.repository.WatchRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class StockServiceImpl implements StockService {

    @Inject
    StockRepository stockRepository;

    @Inject
    WatchRepository watchRepository;

    @Transactional
    @Override
    public List<StockResponseDTO> findAll() {
        return stockRepository
                .listAll()
                .stream()
                .map(StockResponseDTO::valueOf).toList();
    }

    @Override
    public StockResponseDTO findStockById(Long id) {
        if (stockRepository.findById(id) == null) {
            throw new NotFoundException("id stock not found");
        }
        return StockResponseDTO.valueOf(stockRepository.findById(id));
    }

    @Transactional
    @Override
    public StockResponseDTO create(StockDTO stockDTO) {
        Stock stock = new Stock();
        Watch watch = watchRepository.findById(stockDTO.idWatch());
       if (watch == null) {
           throw new NotFoundException("id watch not found");
       }
        stock.setWatch(watch);
        stock.setQuantity(stockDTO.quantity());
        stockRepository.persist(stock);

        return StockResponseDTO.valueOf(stock);
    }

    @Transactional
    @Override
    public void update(Long id, StockDTO stockDTO) {
        Stock stock = stockRepository.findById(id);
        if (stock == null) {
            throw new NotFoundException("id stock not found");
        }
        Watch watch = watchRepository.findById(stockDTO.idWatch());
        if (watch == null) {
            throw new NotFoundException("id watch not found");
        }
        stock.setWatch(watch);
        stock.setQuantity(stockDTO.quantity());
        stockRepository.persist(stock);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (stockRepository.findById(id) == null) {
            throw new NotFoundException("id stock not found");
        }
        stockRepository.deleteById(id);
    }
}
