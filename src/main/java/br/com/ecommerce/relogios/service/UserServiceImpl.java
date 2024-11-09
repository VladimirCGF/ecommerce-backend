package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.UserDTO;
import br.com.ecommerce.relogios.dto.UserResponseDTO;
import br.com.ecommerce.relogios.exceptions.EmailAlreadyInUseException;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.model.Client;
import br.com.ecommerce.relogios.model.Role;
import br.com.ecommerce.relogios.model.User;
import br.com.ecommerce.relogios.repository.UserRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    HashService hashService;

    @Inject
    SecurityIdentity securityIdentity;

    @Transactional
    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository
                .listAll()
                .stream()
                .map(UserResponseDTO::valueOf).toList();
    }

    @Override
    public UserResponseDTO findUserById(Long id) {
        if (userRepository.findById(id) == null) {
            throw new NotFoundException("id user not found");
        }
        return UserResponseDTO.valueOf(userRepository.findById(id));
    }

    @Transactional
    @Override
    public UserResponseDTO create(UserDTO userDTO) {
        User user = new User();
        validUserDTO(userDTO);
        user.setEmail(userDTO.email());
        user.setPassword(hashService.getHashPassword(userDTO.password()));
        user.setRole(Role.CLIENT);
        userRepository.persist(user);
        return UserResponseDTO.valueOf(user);
    }

    @Transactional
    @Override
    public void update(Long id, UserDTO userDTO) {
        validUserDTO(userDTO);
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("User não encontrado");
        }
        user.setPassword(hashService.getHashPassword(userDTO.password()));
        userRepository.persist(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (userRepository.findById(id) == null) {
            throw new NotFoundException("id user not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getLoggedUser() {
        String email = securityIdentity.getPrincipal().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

    @Transactional
    @Override
    public UserResponseDTO login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new ValidationException("O login: email ou senha incorreta");
        }
        return UserResponseDTO.valueOf(user);
    }

    private void validUserDTO(UserDTO userDTO) {
        if (userRepository.find("email", userDTO.email()).count() > 0) {
            throw new EmailAlreadyInUseException("O email já está em uso.");
        }
        if (userDTO.password() == null || userDTO.password().isEmpty()) {
            throw new ValidationException("A senha é obrigatória.");
        }
        if (userDTO.password().length() < 3) {
            throw new ValidationException("A senha deve ter no mínimo 8 caracteres.");
        }
    }
}
