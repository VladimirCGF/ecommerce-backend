package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Storage;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class StorageRepository implements PanacheRepository<Storage> {

    public List<Storage> findAllByWatchId(Long id) {
        return find("watch.id", id).list();
    }

    public PanacheQuery<Storage> findByName(String name) {
        if (name == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + name.toUpperCase() + "%");
    }

    public Boolean existsByName(String name) {
        return find("UPPER(storage.name)  = ?1", name.toUpperCase()) != null;

    }


}
