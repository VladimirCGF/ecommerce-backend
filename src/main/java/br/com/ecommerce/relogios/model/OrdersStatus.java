package br.com.ecommerce.relogios.model;

import br.com.ecommerce.relogios.exceptions.ValidationException;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrdersStatus {
    PENDING(1, "Pendente"),
    WAITING_FOR_PAYMENT(2, "Esperando Pagamento"),
    PAID(3, "Pago"),
    CANCELLED(4, "Cancelado"),
    COMPLETED(5, "Concluído"),
    SHIPPED(6, "Enviado"),
    DELIVERED(7, "Entregue");


    private final int id;
    private final String name;

    OrdersStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static OrdersStatus valueOf(Integer id) {
        for (OrdersStatus ordersStatus : OrdersStatus.values()) {
            if (ordersStatus.id == id)
                return ordersStatus;
        }
        throw new ValidationException("ID statusOrders invalid.");
    }
}
