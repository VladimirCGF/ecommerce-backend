package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {

    @Inject
    EntityManager entityManager;

    public List<Payment> findPaymentsByClientId(Long idClient) {

        String jpql = "SELECT p FROM Payment p " +
                "JOIN p.orders o " +
                "WHERE o.client.id = :clientId";
        TypedQuery<Payment> query = entityManager.createQuery(jpql, Payment.class);
        query.setParameter("clientId", idClient);
        List<Payment> payments = query.getResultList();
        return payments;
    }



}
