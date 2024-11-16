package br.com.ecommerce.relogios.dto;

public record StorageDTO(
        Long idWatch,
        String name) {
    public StorageDTO(Long idWatch, String name) {
        this.idWatch = idWatch;
        this.name = name;
    }
}
