package br.com.ecommerce.relogios.dto;

public record PaymentDTO(
        Integer paymentMethod,
        Integer paymentStatus,
        Long idOrders) {
}
