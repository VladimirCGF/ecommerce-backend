package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.User;
import br.com.ecommerce.relogios.model.Watch;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class WatchRepository implements PanacheRepository<Watch> {

    public List<Watch> findWatchesByOrderId(Long orderId) {
        return find("""
                SELECT w FROM Watch w 
                JOIN w.orderItems oi 
                JOIN oi.orders o 
                WHERE o.id = ?1
                """, orderId).list();
    }

    public List<Watch> findByName(String name) {
        return find("UPPER(name) LIKE ?1", "%" + name.toUpperCase() + "%")
                .stream()
                .toList();
    }

}
