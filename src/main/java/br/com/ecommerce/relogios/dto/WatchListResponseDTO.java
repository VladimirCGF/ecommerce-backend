package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Storage;
import br.com.ecommerce.relogios.model.Watch;

import java.util.List;

public record WatchListResponseDTO(
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
        Storage imagePerfil,
        List<Storage> imageUrls) {
    public static WatchListResponseDTO valueOf(Watch watch) {
        List<Storage> list = watch.getStorages().stream().toList();
        return new WatchListResponseDTO(
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
                list);
    }
}
