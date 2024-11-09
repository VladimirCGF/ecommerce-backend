package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.OrderItemDTO;
import br.com.ecommerce.relogios.dto.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {

    public List<OrderItemResponseDTO> findAll();

    public OrderItemResponseDTO findOrderItemById(Long id);

    public OrderItemResponseDTO create(OrderItemDTO orderItemDTO);

    public void update(Long id, OrderItemDTO orderItemDTO);

    public void delete(Long id);
}
