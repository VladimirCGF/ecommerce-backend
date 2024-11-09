package br.com.ecommerce.relogios.dto;

import java.time.LocalDate;

public record CouponDTO(
        String code,
        Double discountPercentage,
        LocalDate validUntil) {
}
