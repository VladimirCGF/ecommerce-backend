package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.State;

public record StateResponseDTO(
        Long id,
        String abbreviations,
        String name) {
    public static StateResponseDTO valueOf(State state) {
        return new StateResponseDTO(
                state.getId(),
                state.getAbbreviations(),
                state.getName());
    }
}
