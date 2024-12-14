package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.OrderItemDTO;
import br.com.ecommerce.relogios.dto.OrderItemListResponseDTO;
import br.com.ecommerce.relogios.dto.OrderItemResponseDTO;
import br.com.ecommerce.relogios.model.OrderItem;
import br.com.ecommerce.relogios.model.Orders;
import br.com.ecommerce.relogios.model.Watch;
import br.com.ecommerce.relogios.repository.OrderItemRepository;
import br.com.ecommerce.relogios.repository.OrdersRepository;
import br.com.ecommerce.relogios.repository.WatchRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderItemServiceImpl implements OrderItemService {

    @Inject
    OrderItemRepository orderItemRepository;

    @Inject
    OrdersRepository ordersRepository;

    @Inject
    WatchRepository watchRepository;

    @Transactional
    @Override
    public List<OrderItemResponseDTO> findAll() {
        return orderItemRepository
                .listAll()
                .stream()
                .map(OrderItemResponseDTO::valueOf).toList();
    }

    @Override
    public OrderItemResponseDTO findOrderItemById(Long id) {
        if (orderItemRepository.findById(id) == null) {
            throw new NotFoundException("id user not found");
        }
        return OrderItemResponseDTO.valueOf(orderItemRepository.findById(id));
    }

    @Transactional
    @Override
    public OrderItemResponseDTO create(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        Orders orders = ordersRepository.findById(orderItemDTO.idOrders());
        if (orders == null) {
            throw new NotFoundException("id orders not found");
        }
        orderItem.setOrders(orders);
        Watch watch = watchRepository.findById(orderItemDTO.idWatch());
        if (watch == null) {
            throw new NotFoundException("id watch not found");
        }
        double discount = orders.getCoupon() != null ? orders.getCoupon().getDiscountPercentage() : 0.0;
        orderItem.setWatch(watch);
        orderItem.setQuantity(orderItemDTO.quantity());
        orderItem.setPrice(watch.getPrice() * orderItem.getQuantity());
        double totalItemsPrice = orders.getItems().stream()
                .mapToDouble(item -> item.getPrice())
                .sum() + orderItem.getPrice();
        double finalTotalPrice = totalItemsPrice - (totalItemsPrice * (discount / 100));

        orders.setTotalPrice(finalTotalPrice);
        orders.setOrderDate(LocalDateTime.now());

        ordersRepository.persist(orders);

        orderItemRepository.persist(orderItem);
        return OrderItemResponseDTO.valueOf(orderItem);
    }

    @Transactional
    @Override
    public void update(Long id, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(id);
        if (orderItem == null) {
            throw new NotFoundException("id user not found");
        }
        Orders orders = ordersRepository.findById(orderItemDTO.idOrders());
        if (orders == null) {
            throw new NotFoundException("id user not found");
        }
        orderItem.setOrders(orders);
        Watch watch = watchRepository.findById(orderItemDTO.idWatch());
        if (watch == null) {
            throw new NotFoundException("id watch not found");
        }
        orderItem.setWatch(watch);
        orderItem.setQuantity(orderItemDTO.quantity());
        orderItem.setPrice(orderItemDTO.price() * orderItem.getQuantity());
        orderItemRepository.persist(orderItem);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (orderItemRepository.findById(id) == null) {
            throw new NotFoundException("id user not found");
        }
        orderItemRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void addQuantity(Long idOrderItem, Integer quantity) {
        OrderItem orderItem = orderItemRepository.findById(idOrderItem);

        if (orderItem == null) {
            throw new NotFoundException("id order item not found");
        }

        orderItem.setQuantity(orderItem.getQuantity() + quantity);
        orderItem.setPrice(orderItem.getWatch().getPrice() * orderItem.getQuantity());

        Orders order = orderItem.getOrders();
        double totalPrice = order.getItems()
                .stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();

        order.setTotalPrice(totalPrice);
        orderItemRepository.persist(orderItem);
        ordersRepository.persist(order);
    }

    @Transactional
    @Override
    public List<OrderItemListResponseDTO> getWatchesByOrderId(Long orderId) {
        List<OrderItem> orderItemList = orderItemRepository.findByIdOrder(orderId);
        return orderItemList.stream().map(orderItem -> {
                    return new OrderItemListResponseDTO(
                            orderItem.getId(),
                            orderItem.getWatch().getId(),
                            orderItem.getWatch().getImagePerfil().getName(),
                            orderItem.getWatch().getName(),
                            orderItem.getPrice(),
                            orderItem.getQuantity()
                    );
                }
        ).toList();
    }


    @Transactional
    @Override
    public void removeQuantity(Long idOrderItem, Integer quantity) {
        OrderItem orderItem = orderItemRepository.findById(idOrderItem);
        if (orderItem == null) {
            throw new NotFoundException("id order item not found");
        }
        orderItem.setQuantity(orderItem.getQuantity() - quantity);
        orderItem.setPrice(orderItem.getWatch().getPrice() * orderItem.getQuantity());

        Orders order = orderItem.getOrders();
        double totalPrice = order.getItems()
                .stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();

        order.setTotalPrice(totalPrice);
        orderItemRepository.persist(orderItem);
        ordersRepository.persist(order);
    }
}
