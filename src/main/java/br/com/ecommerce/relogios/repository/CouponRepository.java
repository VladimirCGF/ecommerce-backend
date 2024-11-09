package br.com.ecommerce.relogios.repository;

import br.com.ecommerce.relogios.model.Coupon;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CouponRepository implements PanacheRepository<Coupon> {

    public Coupon findByCode(String code) {
        return find("code", code.toUpperCase()).firstResult();
    }


}
