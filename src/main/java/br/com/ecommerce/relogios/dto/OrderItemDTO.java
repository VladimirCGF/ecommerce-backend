package br.com.ecommerce.relogios.dto;

public record OrderItemDTO(
        Long idOrders,
        Long idWatch,
        Integer quantity,
        Double price
) {
}
