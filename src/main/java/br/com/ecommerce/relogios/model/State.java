package br.com.ecommerce.relogios.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "state")
public class State extends DefaultEntity {

    private String abbreviations;

    private String name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    List<Municipality> municipalities = new ArrayList<>();

    public String getAbbreviations() {
        return abbreviations;
    }

    public void setAbbreviations(String abbreviations) {
        this.abbreviations = abbreviations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Municipality> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<Municipality> cities) {
        this.municipalities = cities;
    }
}
