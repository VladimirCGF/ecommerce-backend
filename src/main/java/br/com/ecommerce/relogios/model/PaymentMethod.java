package br.com.ecommerce.relogios.model;


import br.com.ecommerce.relogios.exceptions.ValidationException;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentMethod {
    CREDIT_CARD(1, "Cartão de crédito"),
    DEBIT_CARD(2, "Cartão de débito"),
    PIX(3, "Pix");

    private final int id;
    private final String name;

    PaymentMethod(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PaymentMethod valueOf(Integer id) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.id == id)
                return paymentMethod;
        }
        throw new ValidationException("ID paymentMethod Invalid.");
    }
}
