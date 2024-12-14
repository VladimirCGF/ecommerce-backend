package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.OrderItem;

public record OrderItemResponseDTO(
        Long id,
        Long idOrders,
        Integer quantity,
        Double price,
        WatchResponseDTO idWatch) {

    public static OrderItemResponseDTO valueOf(OrderItem orderItem) {
        WatchResponseDTO watch = WatchResponseDTO.valueOf(orderItem.getWatch());
        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getOrders().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                watch);
    }
}
