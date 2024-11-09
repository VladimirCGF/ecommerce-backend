package br.com.ecommerce.relogios.model.converterjpa;

import br.com.ecommerce.relogios.model.OrdersStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrdersStatusConverter implements AttributeConverter<OrdersStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrdersStatus ordersStatus) {
        return ordersStatus.getId();
    }

    @Override
    public OrdersStatus convertToEntityAttribute(Integer id) {
        return OrdersStatus.valueOf(id);
    }
}
