package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Municipality;

public record MunicipalityResponseDTO(
        Long id,
        String name,
        String state) {
    public static MunicipalityResponseDTO valueOf(Municipality municipality) {
        return new MunicipalityResponseDTO(
                municipality.getId(),
                municipality.getName(),
                municipality.getState().getAbbreviations());
    }
}
