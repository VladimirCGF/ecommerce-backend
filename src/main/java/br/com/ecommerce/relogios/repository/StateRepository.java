package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.State;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StateRepository implements PanacheRepository<State> {

    public State findByName(String name) {
        return find("UPPER(name)", name.toUpperCase()).firstResult();
    }

    public State findByAbbreviations(String abbreviations) {
        return find("UPPER(abbreviations)", abbreviations.toUpperCase()).firstResult();
    }
}
