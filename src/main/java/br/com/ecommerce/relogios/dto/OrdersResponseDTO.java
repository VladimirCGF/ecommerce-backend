package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Orders;

import java.time.LocalDateTime;
import java.util.List;

public record OrdersResponseDTO(
        Long id,
        LocalDateTime orderDate,
        Double totalPrice,
        Long idClient,
        Long idAddress,
        String status,
        String coupon,
        List<OrderItemResponseDTO> orderItems) {
    public static OrdersResponseDTO valueOf(Orders orders) {
        List<OrderItemResponseDTO> orderItems = orders.getItems().stream().map(OrderItemResponseDTO::valueOf).toList();
        return new OrdersResponseDTO(
                orders.getId(),
                orders.getOrderDate(),
                orders.getTotalPrice(),
                orders.getClient().getId(),
                orders.getAddress() != null ? orders.getAddress().getId() : null,
                orders.getStatus().getName(),
                orders.getCoupon() != null ? orders.getCoupon().getCode() : null,
                orderItems);
    }
}
