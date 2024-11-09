package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Client;

public record ClientResponseDTO(
        Long id,
        String email,
        String firstName,
        String lastName,
        String cpf) {

    public static ClientResponseDTO valueOf(Client client){
        return new ClientResponseDTO(
                client.getId(),
                client.getUser().getEmail(),
                client.getFirstName(),
                client.getLastName(),
                client.getCpf());
    }
}
