import java.time.LocalDate;

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
@Table(name="Tuition")
@JsonPropertyOrder({"id", "status", "dateApply", "amount"})
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    public Long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    public Student student;

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


