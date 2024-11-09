package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.EmployeeDTO;
import br.com.ecommerce.relogios.dto.EmployeeResponseDTO;
import br.com.ecommerce.relogios.exceptions.EmailAlreadyInUseException;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.model.*;
import br.com.ecommerce.relogios.repository.EmployeeRepository;
import br.com.ecommerce.relogios.repository.UserRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    HashService hashService;

    @Inject
    SecurityIdentity securityIdentity;

    @Transactional
    @Override
    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository
                .listAll()
                .stream()
                .map(EmployeeResponseDTO::valueOf).toList();
    }

    @Override
    public EmployeeResponseDTO findEmployeeById(Long id) {
        if (employeeRepository.findById(id) == null) {
            throw new NotFoundException("id user not found");
        }
        return EmployeeResponseDTO.valueOf(employeeRepository.findById(id));
    }

    @Transactional
    @Override
    public EmployeeResponseDTO create(EmployeeDTO employeeDTO) {
        validEmployeeDTO(employeeDTO);

        User user = new User();
        user.setEmail(employeeDTO.email());
        user.setPassword(hashService.getHashPassword(employeeDTO.password()));
        user.setRole(Role.EMPLOYEE);
        userRepository.persist(user);

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setFirstName(employeeDTO.firstName());
        employee.setLastName(employeeDTO.lastName());
        employeeRepository.persist(employee);
        return EmployeeResponseDTO.valueOf(employee);
    }

    @Transactional
    @Override
    public void update(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new NotFoundException("id employee not found");
        }
        Employee employeeDB = employeeRepository.findByEmail(employeeDTO.email());
        if (!employeeDB.getId().equals(employee.getId())) {
            throw new ValidationException("id de funcionário deve ser iguais");
        }
        employee.setFirstName(employeeDTO.firstName());
        employee.setLastName(employeeDTO.lastName());
        employee.getUser().setEmail(employeeDTO.email());
        employee.getUser().setPassword(hashService.getHashPassword(employeeDTO.password()));
        employeeRepository.persist(employee);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (employeeRepository.findById(id) == null) {
            throw new NotFoundException("id employee not found");
        }
        employeeRepository.deleteById(id);
    }


    @Transactional
    @Override
    public boolean checkEmailUnique(Long id, String email) {
        String normalizedCode = email.trim().toUpperCase();
        Employee existing = employeeRepository.findByEmail(normalizedCode);
        if (existing.getId().equals(id)) {
            return true;
        }
        return existing == null;
    }

    private void validEmployeeDTO(EmployeeDTO employeeDTO) {
        if (userRepository.find("email", employeeDTO.email()).count() > 0) {
            throw new EmailAlreadyInUseException("O email já está em uso.");
        }
        if (employeeDTO.password() == null || employeeDTO.password().isEmpty()) {
            throw new ValidationException("A senha é obrigatória.");
        }
        if (employeeDTO.password().length() < 3) {
            throw new ValidationException("A senha deve ter no mínimo 8 caracteres.");
        }
    }
}
