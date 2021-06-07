package org.acme.rest.json.entities;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity
@Table(name="Enrollment")
@JsonPropertyOrder({"id", "student", "tuition"})
public class Enrollment {
    // This Entity is only a way of representation of OneToOne in JPA, not SQL obviously

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    public Long id;

    // There's no need to use mappedBy because the Objects Student and tuition are in this table, not in other

    @OneToOne
    @JoinColumn(name = "student_id")
    public Student student;

    // This property has CascadeType, orphanRemoval adn fetch because Tuition have the FK of student_id
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "tuition_id")
    public Tuition tuition;

    public Enrollment() {}


    public Enrollment(Student student, Tuition tuition) {
        this.student = student;
        this.tuition = tuition;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Tuition getTuition() {
        return this.tuition;
    }

    public void setTuition(Tuition tuition) {
        this.tuition = tuition;
    }



    

    
}


