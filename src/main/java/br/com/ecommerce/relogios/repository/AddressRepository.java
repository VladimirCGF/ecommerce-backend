package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Address;
import br.com.ecommerce.relogios.model.OrdersStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {

    public Address findByClientId(Long clientId) {
        return find("status = ?1 and client.id = ?2", OrdersStatus.PENDING, clientId).firstResult();
    }
}
