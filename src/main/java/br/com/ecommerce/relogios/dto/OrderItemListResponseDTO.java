package br.com.ecommerce.relogios.dto;

public record OrderItemListResponseDTO(
        Long idOrderItem,
        Long idWatch,
        String nameImage,
        String name,
        Double price,
        Integer quantity) {
    public OrderItemListResponseDTO valueOf(OrderItemListResponseDTO orderItemListResponseDTO) {
        return new OrderItemListResponseDTO(
                orderItemListResponseDTO.idOrderItem,
                orderItemListResponseDTO.idWatch,
                orderItemListResponseDTO.nameImage,
                orderItemListResponseDTO.name,
                orderItemListResponseDTO.price,
                orderItemListResponseDTO.quantity);
    }
}
