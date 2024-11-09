package br.com.ecommerce.relogios.dto;

public record ClientDTO(
        String email,
        String password,
        String firstName,
        String lastName,
        String cpf) {
}
