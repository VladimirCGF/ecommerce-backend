package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.UserDTO;
import br.com.ecommerce.relogios.dto.UserResponseDTO;
import br.com.ecommerce.relogios.model.User;

import java.util.List;

public interface UserService {

    public List<UserResponseDTO> findAll();

    public UserResponseDTO findUserById(Long id);

    public UserResponseDTO create(UserDTO userDTO);

    public void update(Long id, UserDTO userDTO);

    public void delete(Long id);

    public User getLoggedUser();

    public UserResponseDTO login(String email, String password);


}
