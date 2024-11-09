package br.com.ecommerce.relogios.model;

import br.com.ecommerce.relogios.exceptions.ValidationException;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {
    ADMIN(1, "Admin"),
    EMPLOYEE(2, "Funcionario"),
    CLIENT(3, "Cliente");

    private final int id;
    private final String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role valueOf(Integer id) {
        for (Role role : Role.values()) {
            if (role.id == id)
                return role;
        }
        throw new ValidationException("id role invalid.");
    }

}
