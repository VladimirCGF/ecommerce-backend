package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.OrdersDTO;
import br.com.ecommerce.relogios.dto.OrdersResponseDTO;
import br.com.ecommerce.relogios.model.*;
import br.com.ecommerce.relogios.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrdersServiceImpl implements OrdersService {

    @Inject
    OrdersRepository ordersRepository;

    @Inject
    ClientRepository clientRepository;

    @Inject
    AddressRepository addressRepository;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    OrderItemRepository orderItemRepository;

    @Inject
    CouponRepository couponRepository;


    @Transactional
    @Override
    public List<OrdersResponseDTO> findAll() {
        return ordersRepository
                .listAll()
                .stream()
                .map(OrdersResponseDTO::valueOf).toList();
    }

    @Override
    public OrdersResponseDTO findOrdersById(Long id) {
        if (ordersRepository.findById(id) == null) {
            throw new NotFoundException("id orders not found");
        }
        return OrdersResponseDTO.valueOf(ordersRepository.findById(id));
    }

    @Transactional
    @Override
    public OrdersResponseDTO create(OrdersDTO ordersDTO) {
        Orders orders = new Orders();
        orders.setOrderDate(LocalDateTime.now());
        Client client = clientRepository.findById(ordersDTO.idClient());
        if (client == null) {
            throw new NotFoundException("client not found");
        }
        orders.setClient(client);
        Address address = addressRepository.findById(ordersDTO.idAddress());
        if (address == null) {
            throw new NotFoundException("address not found");
        }
        Coupon coupon = couponRepository.findByCode(ordersDTO.coupon());
        if (coupon == null || ordersDTO.coupon().isEmpty()) {
            orders.setCoupon(null);
        }
        orders.setCoupon(coupon);
        orders.setAddress(address);
        orders.setStatus(OrdersStatus.PENDING);
        ordersRepository.persist(orders);
        return OrdersResponseDTO.valueOf(orders);
    }

    @Transactional
    @Override
    public void update(Long id, OrdersDTO ordersDTO) {
        Orders orders = ordersRepository.findById(id);
        if (orders == null) {
            throw new NotFoundException("id orders not found");
        }
        orders.setOrderDate(LocalDateTime.now());
        Client client = clientRepository.findById(ordersDTO.idClient());
        if (client == null) {
            throw new NotFoundException("client not found");
        }
        orders.setClient(client);
        Address address = addressRepository.findById(ordersDTO.idAddress());
        if (address == null) {
            throw new NotFoundException("address not found");
        }
        // Busca e aplica o cupom se existir
        if (ordersDTO.coupon() != null && !ordersDTO.coupon().isEmpty()) {
            Coupon coupon = couponRepository.findByCode(ordersDTO.coupon());
            if (coupon != null) {
                orders.setCoupon(coupon);
            } else {
                throw new NotFoundException("coupon not found");
            }
        } else {
            orders.setCoupon(null);
        }

        double discount = orders.getCoupon() != null ? orders.getCoupon().getDiscountPercentage() : 0.0;
        double finalTotalPrice = orders.getTotalPrice() - (orders.getTotalPrice() * (discount / 100));
        orders.setTotalPrice(finalTotalPrice);
        orders.setAddress(address);
        ordersRepository.persist(orders);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (ordersRepository.findById(id) == null) {
            throw new NotFoundException("id orders not found");
        }
        ordersRepository.deleteById(id);
    }
}
