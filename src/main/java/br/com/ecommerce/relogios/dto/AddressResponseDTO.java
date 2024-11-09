package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Address;

public record AddressResponseDTO(
        Long id,
        String address,
        String state,
        String municipality,
        String cep,
        Long idClient) {
    public static AddressResponseDTO valueOf(Address address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getAddress(),
                address.getState().getName(),
                address.getMunicipality().getName(),
                address.getCep(),
                address.getClient().getId());
    }
}
