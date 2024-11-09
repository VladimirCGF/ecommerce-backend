package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.User;

public record UserResponseDTO(
        String email,
        String role) {
    public static UserResponseDTO valueOf(User user) {
        return new UserResponseDTO(
                user.getEmail(),
                user.getRole().getName());
    }
}
