package br.com.ecommerce.relogios.model.converterjpa;

import br.com.ecommerce.relogios.model.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentStatus paymentStatus) {
        return paymentStatus.getId();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(Integer id) {
        return PaymentStatus.valueOf(id);
    }
}
