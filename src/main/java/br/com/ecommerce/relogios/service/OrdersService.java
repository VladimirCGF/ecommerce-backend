package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.OrdersDTO;
import br.com.ecommerce.relogios.dto.OrdersResponseDTO;

import java.util.List;

public interface OrdersService {

    public List<OrdersResponseDTO> findAll();

    public OrdersResponseDTO findOrdersById(Long id);

    public OrdersResponseDTO create(OrdersDTO ordersDTO);

    public void update(Long id, OrdersDTO ordersDTO);

    public void delete(Long id);
}
