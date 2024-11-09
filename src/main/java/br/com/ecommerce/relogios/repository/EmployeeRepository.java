package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    public Employee findByEmail(String email) {
        return find("UPPER(user.email) = ?1 ", email.toUpperCase()).firstResult();
    }
}
