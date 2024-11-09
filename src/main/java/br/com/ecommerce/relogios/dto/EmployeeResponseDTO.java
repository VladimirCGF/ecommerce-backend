package br.com.ecommerce.relogios.dto;

import br.com.ecommerce.relogios.model.Employee;

public record EmployeeResponseDTO(
        Long id,
        String email,
        String firstName,
        String lastName) {
    public static EmployeeResponseDTO valueOf(Employee employee) {
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getUser().getEmail(),
                employee.getFirstName(),
                employee.getLastName());

    }
}

