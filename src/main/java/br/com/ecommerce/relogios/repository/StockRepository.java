package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Stock;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StockRepository implements PanacheRepository<Stock> {
}
