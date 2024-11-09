package br.com.ecommerce.relogios.model;

import br.com.ecommerce.relogios.exceptions.ValidationException;

public enum PaymentStatus {
    APPROVED(1, "Aprovado"),
    CANCELLED(2, "Cancelado"),
    PENDING(3, "Pendente");


    private final int id;
    private final String name;

    PaymentStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PaymentStatus valueOf(Integer id) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.id == id)
                return paymentStatus;
        }
        throw new ValidationException("ID statusPayment invalid.");
    }
}
