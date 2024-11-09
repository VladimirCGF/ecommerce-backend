package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Stock;

public record StockResponseDTO(
        Long id,
        Long idWatch,
        Integer quantity) {
    public static StockResponseDTO valueOf(Stock stock) {
        return new StockResponseDTO(
                stock.getId(),
                stock.getWatch().getId(),
                stock.getQuantity());
    }
}
