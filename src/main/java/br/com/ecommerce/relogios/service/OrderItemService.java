package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.OrderItemDTO;
import br.com.ecommerce.relogios.dto.OrderItemListResponseDTO;
import br.com.ecommerce.relogios.dto.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {

    public List<OrderItemResponseDTO> findAll();

    public OrderItemResponseDTO findOrderItemById(Long id);

    public OrderItemResponseDTO create(OrderItemDTO orderItemDTO);

    public void update(Long id, OrderItemDTO orderItemDTO);

    void addQuantity(Long idOrderItem, Integer quantity);

    List<OrderItemListResponseDTO> getWatchesByOrderId(Long orderId);

    void removeQuantity(Long idOrderItem, Integer quantity);

    public void delete(Long id);
}
