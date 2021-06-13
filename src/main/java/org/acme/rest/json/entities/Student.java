package org.acme.rest.json.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name="Student")
@JsonPropertyOrder({"name", "surname", "dateBirth", "phone", "university"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    public Long id;

    @NotEmpty
    @NotBlank
    @Column(name="name", nullable = false)
    public String name;

    @NotEmpty
    @NotBlank
    @Column(name="surname")
    public String surname;


    // @Temporal(TemporalType.DATE) Es para java.Util.Date

    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name="date_birth", nullable = false, columnDefinition = "DATE")
    public LocalDate dateBirth;

    @NotEmpty
    @NotBlank
    @Column(name="phone")
    public String phone;

    // Bidireccional Student - University
    @ManyToOne
    @JoinColumn(name="university_id")
    public University university;

    public Student() {}


    public Student(String name, String surname, LocalDate dateBirth, String phone, University university) {
        this.name = name;
        this.surname = surname;
        this.dateBirth = dateBirth;
        this.phone = phone;
        this.university = university;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateBirth() {
        return this.dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return this.id;
    }

    public University getUniversity() {
        return this.university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
    
    
}
