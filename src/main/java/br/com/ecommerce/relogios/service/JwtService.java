package br.com.ecommerce.relogios.service;


import br.com.ecommerce.relogios.dto.UserResponseDTO;

public interface JwtService {
    String generateJwt(UserResponseDTO userResponseDTO);
}
