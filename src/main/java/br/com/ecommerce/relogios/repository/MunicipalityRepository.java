package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Municipality;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MunicipalityRepository implements PanacheRepository<Municipality> {

    public Municipality findByName(String name) {
        return find("name", name).firstResult();
    }

    public Municipality findByNameAndStateId(String normalizedName, Long stateId) {
        return find("name = ?1 and state.id = ?2", normalizedName, stateId).firstResult();
    }

    public List<Municipality> findByStateId(Long idState) {
        return list("state.id", idState);
    }

}
