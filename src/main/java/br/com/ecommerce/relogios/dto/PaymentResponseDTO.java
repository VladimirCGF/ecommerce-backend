package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Payment;

import java.time.LocalDateTime;

public record PaymentResponseDTO(
        Long id,
        Double price,
        LocalDateTime paymentDateTime,
        String paymentMethod,
        String paymentStatus,
        Long idOrders) {
    public static PaymentResponseDTO valueOf(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getPrice(),
                payment.getPaymentDateTime(),
                payment.getPaymentMethod().getName(),
                payment.getPaymentStatus().getName(),
                payment.getOrders().getId());
    }
}
