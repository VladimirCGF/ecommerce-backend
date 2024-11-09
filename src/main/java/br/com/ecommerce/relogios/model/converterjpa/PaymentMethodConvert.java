package br.com.ecommerce.relogios.model.converterjpa;


import br.com.ecommerce.relogios.model.PaymentMethod;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentMethodConvert implements AttributeConverter<PaymentMethod, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentMethod paymentMethod) {
        return paymentMethod.getId();
    }

    @Override
    public PaymentMethod convertToEntityAttribute(Integer id) {
        return PaymentMethod.valueOf(id);
    }
}


