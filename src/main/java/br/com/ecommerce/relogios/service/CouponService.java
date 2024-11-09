package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.CouponDTO;
import br.com.ecommerce.relogios.dto.CouponResponseDTO;

import java.util.List;

public interface CouponService {

    public List<CouponResponseDTO> findAll();

    public CouponResponseDTO findCouponById(Long id);

    public CouponResponseDTO create(CouponDTO couponDTO);

    public void update(Long id, CouponDTO couponDTO);

    public void delete(Long id);

    public boolean checkCodeUnique(Long id, String code);

    public CouponResponseDTO findByCode(String code);
}
