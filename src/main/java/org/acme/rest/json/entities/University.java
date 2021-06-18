package org.acme.rest.json.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "University")
@JsonPropertyOrder({"name", "address", "email", "phone"})
public class University {
    

    @Id
    @NotBlank
    @Column(name = "name", nullable = false)
    public String name;

    @NotBlank
    @Column(name = "address", nullable = false)
    public String address;

    @NotBlank
    @Column(name = "email", nullable = false)
    public String email;

    @NotBlank
    @Column(name = "phone", nullable = false, length = 22)
    public String phone;

    // Bidireccional Student - University
    // Remember: This property doesn't have @Column or @JoinColumn because we are in JPA context, not SQL. We decide to do a Bidrectional relational in JPA, but in SQL still the same structure. This is because we decide to get information about how many Students has a University
    // And we mappedBy each universtity property from Student Entity to related with their actual University Entity.
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    public University() {}


    public University(String name, String address, String email, String phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    

}
