package com.PortfolioManager.domain.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Consultant implements Serializable {

    private String name;
    private String email;

    public Consultant() {
        // TODO Auto-generated constructor stub
    }

    public Consultant(String email) {
        super();
        this.email = email;
    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "\n-------------------------\nConsultant: " + name + "\nEmail: "
                + email + "\n-------------------------\n";
    }
}
