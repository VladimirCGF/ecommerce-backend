package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByEmailAndPassword(String email, String password) {
        return find("UPPER(email) = ?1 AND password = ?2", email.toUpperCase(), password).firstResult();
    }

    public User findByEmail(String email) {
        return find("UPPER(email) = ?1 ", email.toUpperCase()).firstResult();
    }
}
