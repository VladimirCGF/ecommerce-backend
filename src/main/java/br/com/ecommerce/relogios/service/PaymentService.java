package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.PaymentDTO;
import br.com.ecommerce.relogios.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    public List<PaymentResponseDTO> findAll();

    public PaymentResponseDTO findPaymentById(Long id);

    public PaymentResponseDTO create(PaymentDTO paymentDTO);

    public void update(Long id, PaymentDTO paymentDTO);

    public void delete(Long id);
}
