package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.CouponDTO;
import br.com.ecommerce.relogios.dto.CouponResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.model.Coupon;
import br.com.ecommerce.relogios.repository.CouponRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CouponServiceImpl implements CouponService {

    @Inject
    CouponRepository couponRepository;

    @Transactional
    @Override
    public List<CouponResponseDTO> findAll() {
        return couponRepository
                .listAll()
                .stream()
                .map(CouponResponseDTO::valueOf).toList();
    }

    @Override
    public CouponResponseDTO findCouponById(Long id) {
        if (couponRepository.findById(id) == null) {
            throw new NotFoundException("id coupon not found");
        }
        return CouponResponseDTO.valueOf(couponRepository.findById(id));
    }

    @Transactional
    @Override
    public CouponResponseDTO create(CouponDTO couponDTO) {
        validCouponDTO(couponDTO);
        Coupon coupon = new Coupon();
        coupon.setCode(couponDTO.code().toUpperCase());
        coupon.setDiscountPercentage(couponDTO.discountPercentage());
        coupon.setValidUntil(couponDTO.validUntil());
        couponRepository.persist(coupon);
        return CouponResponseDTO.valueOf(coupon);
    }

    @Transactional
    @Override
    public void update(Long id, CouponDTO couponDTO) {
        Coupon coupon = couponRepository.findById(id);
        if (coupon == null) {
            throw new NotFoundException("id coupon not found");
        }
        Coupon couponDB = couponRepository.findByCode(couponDTO.code());
        if (!couponDB.getId().equals(coupon.getId())) {
            throw new ValidationException("id de coupon deve ser iguais");
        }
        coupon.setCode(couponDTO.code().toUpperCase());
        coupon.setDiscountPercentage(couponDTO.discountPercentage());
        coupon.setValidUntil(couponDTO.validUntil());
        couponRepository.persist(coupon);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (couponRepository.findById(id) == null) {
            throw new NotFoundException("id coupon not found");
        }
        couponRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean checkCodeUnique(Long id, String code) {
        String normalizedCode = code.trim().toUpperCase();
        Coupon existingCoupon = couponRepository.findByCode(normalizedCode);
        if (existingCoupon.getId().equals(id)) {
            return true;
        }
        return existingCoupon == null;
    }

//    @Override
//    public boolean validCode(Long id, CouponDTO couponDTO) {
//        Coupon coupon = couponRepository.findByCode(couponDTO.code());
//        if (coupon == null) {
//            return true;
//        }
//        if (id != null && coupon.getId().equals(id)) {
//            return true;
//        }
//        return false;
//    }


    @Transactional
    @Override
    public CouponResponseDTO findByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code);
        if (coupon == null) {
            throw new NotFoundException("coupon not found");
        }
        return CouponResponseDTO.valueOf(coupon);
    }

    private void validCouponDTO(CouponDTO couponDTO) {
        if (couponDTO.code() == null || couponDTO.code().isEmpty()) {
            throw new ValidationException("O código é obrigatório.");
        }

        if (couponRepository.find("code", couponDTO.code().toUpperCase()).count() > 0) {
            throw new ValidationException("O código do cupom já existe.");
        }

        if (couponDTO.discountPercentage() <= 0) {
            throw new ValidationException("A porcentagem de desconto deve ser maior que 0.");
        }
    }
}
