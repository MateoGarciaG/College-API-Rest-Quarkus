package org.acme.rest.json.entities;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity
@Table(name="Enrollment")
@JsonPropertyOrder({"student", "tuition"})
public class Enrollment {
    // This Entity is only a way of representation of OneToOne in JPA, not SQL obviously

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    // There's no need to use mappedBy because the Objects Student and tuition are in this table, not in other

    @OneToOne
    @JoinColumn(name = "id_student")
    public Student student;


    // @OneToOne
    // This property has CascadeType, orphanRemoval adn fetch because Tuition have the FK of student_id and
    // In JPA, if we don't create a brigde Table like this "Enrollment" Entity, the FK object Student will be in Tuition Entity in the case of being Unidirectional
    //  In the case of being Bidirectional, we need to add The Object Tuition to Student Entity which Student it's the father entity of the relation, because A Tuition cannot exists without a student, so we put the CascadeType, mappedBy and FetchType in Tuition Object which will be in the Father Entity which it's Student.
    // Example OneToOne without Enrollment Entity:

    /* 
    ? In Student Entity: It's the father Entity of the relation

    * As we can see this property doesn't have @JoinColumn or @Column because it's not present in SQL tables, only in JPA context

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Tuition tuition;

    ? In Tuition Entity: It's the child Entity of the relation, depends on Student Entity

    * Due to A Tuition depends on Student, In SQL A Tuition has a FK of Student, in JPA it's the same with @JoinColumn to indicate that it's a FK Student.
    
    @JoinColumn(name = "student_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Student student;
    
    
    */


    // In this case where we have "Enrollment", it's similar because the CascadeType and FetchType it's put in the Tuition Object to simulate the Father Entity over the Child Entity which it's Tuition and the mappedBy it's innecessary because both Student and Tuition Objects are in this Entity "Enrollment":
    // @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tuition")
    public Tuition tuition;

    // @JsonFormat(shape = JsonFormat.Shape.STRING,
    // pattern = "YYYY-MM-DD HH:mm:SS", timezone = JsonFormat.DEFAULT_TIMEZONE)
    // @Column(name="created", nullable = false, columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:SS")
    @Column(name = "created", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime created = LocalDateTime.now();
    // public LocalDateTime created = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    // public LocalDateTime created;


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

    // public LocalDateTime getCreatedDate() {
    //     return this.created;
    // }

    // public void setCreatedDate(LocalDateTime created) {
    //     this.created = created;
    // }

    
}


