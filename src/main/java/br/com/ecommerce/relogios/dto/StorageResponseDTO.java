package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Storage;

public record StorageResponseDTO(
        Long id,
        Long idWatch,
        String name,
        String url) {
    public static StorageResponseDTO valueOf(Storage storage) {
        return new StorageResponseDTO(
                storage.getId(),
                storage.getWatch().getId(),
                storage.getName(),
                storage.getUrl());
    }
}
