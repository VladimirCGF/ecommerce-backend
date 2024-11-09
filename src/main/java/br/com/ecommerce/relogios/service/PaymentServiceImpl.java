package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.PaymentDTO;
import br.com.ecommerce.relogios.dto.PaymentResponseDTO;
import br.com.ecommerce.relogios.model.*;
import br.com.ecommerce.relogios.repository.OrdersRepository;
import br.com.ecommerce.relogios.repository.PaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PaymentServiceImpl implements PaymentService {

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    OrdersRepository ordersRepository;

    @Transactional
    @Override
    public List<PaymentResponseDTO> findAll() {
        return paymentRepository
                .listAll()
                .stream()
                .map(PaymentResponseDTO::valueOf).toList();
    }

    @Override
    public PaymentResponseDTO findPaymentById(Long id) {
        if (paymentRepository.findById(id) == null) {
            throw new NotFoundException("id stock not found");
        }
        return PaymentResponseDTO.valueOf(paymentRepository.findById(id));
    }

    @Transactional
    @Override
    public PaymentResponseDTO create(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        Orders orders = ordersRepository.findById(paymentDTO.idOrders());
        if (orders == null) {
            throw new NotFoundException("orders not found");
        }
        payment.setOrders(orders);
        payment.setPaymentDateTime(LocalDateTime.now());
        payment.setPrice(orders.getTotalPrice());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentDTO.paymentMethod()));
        payment.setPaymentStatus(PaymentStatus.APPROVED);
        orders.setStatus(OrdersStatus.PAID);
        paymentRepository.persist(payment);
        return PaymentResponseDTO.valueOf(payment);
    }

    @Transactional
    @Override
    public void update(Long id, PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(id);
        if (payment == null) {
            throw new NotFoundException("id payment not found");
        }
        Orders orders = ordersRepository.findById(paymentDTO.idOrders());
        if (orders == null) {
            throw new NotFoundException("orders not found");
        }
        payment.setPrice(orders.getTotalPrice());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentDTO.paymentMethod()));
        paymentRepository.persist(payment);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (paymentRepository.findById(id) == null) {
            throw new NotFoundException("id payment not found");
        }
        paymentRepository.deleteById(id);

    }
}
