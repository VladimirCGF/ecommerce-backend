package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Orders;
import br.com.ecommerce.relogios.model.OrdersStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrdersRepository implements PanacheRepository<Orders> {

    @Inject
    EntityManager entityManager;

    public List<Orders> findListByIdCliente(Long id) {
        return find("client.id", id).list();
    }

    public Orders findPendingOrderByClientId(Long clientId) {
        return find("status = ?1 and client.id = ?2", OrdersStatus.PENDING, clientId).firstResult();
    }

    public List<Orders> findExpiredOrders(LocalDateTime now) {
        String jpql = "SELECT o FROM Orders o WHERE o.status = :status AND o.paymentDeadline < :now";
        TypedQuery<Orders> query = entityManager.createQuery(jpql, Orders.class);
        query.setParameter("status", OrdersStatus.WAITING_FOR_PAYMENT);
        query.setParameter("now", now);
        return query.getResultList();
    }
}
