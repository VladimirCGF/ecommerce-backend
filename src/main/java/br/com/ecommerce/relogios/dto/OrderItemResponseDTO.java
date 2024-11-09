package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.OrderItem;

public record OrderItemResponseDTO(
        Long id,
        Long idOrders,
        Long idWatch,
        Integer quantity,
        Double price) {

    public static OrderItemResponseDTO valueOf(OrderItem orderItem) {
        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getOrders().getId(),
                orderItem.getWatch().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice());
    }
}
