package br.com.ecommerce.relogios.dto;

public record WatchDTO(
        String name,
        String description,
        Double price,
        String material,
        String color,
        String gender,
        String brand,
        String format,
        String mechanism) {
}
