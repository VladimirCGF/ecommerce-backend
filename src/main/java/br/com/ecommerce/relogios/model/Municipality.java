package br.com.ecommerce.relogios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "municipality")
public class Municipality extends DefaultEntity {

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    private String name;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
