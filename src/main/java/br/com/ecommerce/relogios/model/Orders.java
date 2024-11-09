package br.com.ecommerce.relogios.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders extends DefaultEntity {

    private LocalDateTime orderDate;

    private Double totalPrice;

    private OrdersStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = true)
    private Coupon coupon;

    private LocalDateTime paymentDeadline;

    public LocalDateTime getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(LocalDateTime paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        if (totalPrice == null) {
            totalPrice = 0.0;
        }
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        if (totalPrice == null) {
            totalPrice = 0.0;
        }
        this.totalPrice = totalPrice;
    }

    public void addToTotal(Double amount) {
        if (totalPrice == null) {
            totalPrice = 0.0;
        }
        this.totalPrice += amount;
    }

    public void subtractFromTotal(Double amount) {
        if (totalPrice == null) {
            totalPrice = 0.0;
        }
        this.totalPrice -= amount;
    }

    public OrdersStatus getStatus() {
        return status;
    }

    public void setStatus(OrdersStatus status) {
        this.status = status;
    }

    public void applyCoupon() {
        if (coupon != null && totalPrice > 0) {
            Double discount = totalPrice * (coupon.getDiscountPercentage() / 100);
            this.subtractFromTotal(discount);
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public List<OrderItem> getOrderItems() {
        return items;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.items = orderItems;
    }

}
