package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.OrderItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OrderItemRepository implements PanacheRepository<OrderItem> {

    public List<OrderItem> findByIdOrder(Long idOrder) {
        return find("orders.id", idOrder).stream().toList();
    }



}
