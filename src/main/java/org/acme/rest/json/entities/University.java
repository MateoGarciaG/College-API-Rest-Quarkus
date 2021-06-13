package org.acme.rest.json.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "University")
@JsonPropertyOrder({"name", "address", "email", "phone"})
public class University {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    public String name;

    @NotBlank
    @Column(name = "address", nullable = false)
    public String address;

    @NotBlank
    @Column(name = "email", nullable = false, length = 22)
    public String phone;

    public University() {}


    public University(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    

}
