package br.com.ecommerce.relogios.dto;

public record AddressDTO(
        String address,
        Long idState,
        Long idMunicipality,
        String cep,
        Long idClient) {
}
