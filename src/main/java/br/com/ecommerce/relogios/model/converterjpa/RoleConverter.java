package br.com.ecommerce.relogios.model.converterjpa;

import br.com.ecommerce.relogios.model.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
       return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Integer id) {
        return Role.valueOf(id);
    }
    
}