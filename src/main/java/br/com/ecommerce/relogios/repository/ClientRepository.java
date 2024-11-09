package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Client;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    public Client findByEmail(String email) {
        return find("UPPER(user.email) = ?1 ", email.toUpperCase()).firstResult();
    }

    public Client findByCpf(String cpf) {
        return find("cpf" , cpf).firstResult();
    }
}
