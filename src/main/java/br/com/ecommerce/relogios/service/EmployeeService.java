package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.EmployeeDTO;
import br.com.ecommerce.relogios.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    public List<EmployeeResponseDTO> findAll();

    public EmployeeResponseDTO findEmployeeById(Long id);

    public EmployeeResponseDTO create(EmployeeDTO employeeDTO);

    public void update(Long id, EmployeeDTO employeeDTO);

    public void delete(Long id);

    boolean checkEmailUnique(Long id, String email);
}
