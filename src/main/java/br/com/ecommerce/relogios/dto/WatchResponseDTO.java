package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Watch;

import java.util.List;

public record WatchResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        String material,
        String color,
        String gender,
        String brand,
        String format,
        String mechanism,
        String imagePerfil,
        List<String> imageUrls) {
    public static WatchResponseDTO valueOf(Watch watch) {
        List<String> imagens = watch.getImageUrls().stream().toList();
        return new WatchResponseDTO(
                watch.getId(),
                watch.getName(),
                watch.getDescription(),
                watch.getPrice(),
                watch.getMaterial(),
                watch.getColor(),
                watch.getGender(),
                watch.getBrand(),
                watch.getFormat(),
                watch.getMechanism(),
                watch.getImagePerfil(),
                imagens);
    }
}
