package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Coupon;

import java.time.LocalDate;

public record CouponResponseDTO(
        Long id,
        String code,
        Double discountPercentage,
        LocalDate validUntil) {

    public static CouponResponseDTO valueOf(Coupon coupon) {
        return new CouponResponseDTO(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDiscountPercentage(),
                coupon.getValidUntil());
    }
}
