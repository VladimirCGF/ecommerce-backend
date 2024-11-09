package br.com.ecommerce.relogios.dto;

public record OrdersDTO(
        Long idClient,
        Long idAddress,
        String coupon,
        Integer status) {
}
