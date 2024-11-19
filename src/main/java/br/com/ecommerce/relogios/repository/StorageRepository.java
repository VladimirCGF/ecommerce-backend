package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Storage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class StorageRepository implements PanacheRepository<Storage> {

    public List<Storage> findAllByWatchId(Long id) {
        return find("watch.id", id).list();
    }

    public boolean existsByName(String name) {
        return count("UPPER(name) = ?1", name.toUpperCase()) > 0;
    }


}
