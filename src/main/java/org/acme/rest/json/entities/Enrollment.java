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

    // This property has CascadeType, orphanRemoval adn fetch because Tuition have the FK of student_id
    // @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    @OneToOne
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


