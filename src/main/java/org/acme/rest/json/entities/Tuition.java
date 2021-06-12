package org.acme.rest.json.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity
@Table(name="Tuition")
@JsonPropertyOrder({"id", "status", "dateApply", "amount"})
public class Tuition {

    @Id
	@Column(name = "id", updatable = false, nullable = false, unique = true)
    public Long id;

    @Column(name="status_t", nullable = false)
    private Boolean status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name="date_apply", nullable = false, columnDefinition = "DATE")
    public LocalDate dateApply;

    
    @Column(name="amount", nullable = false)
    public Double amount;

    // @OneToOne(mappedBy="student_id")
    // public Enrollment enrollment;


    public Tuition() {}



    public Tuition(Long id, Boolean status, LocalDate dateApply, Double amount) {
        this.id = id;
        this.status = status;
        this.dateApply = dateApply;
        this.amount = amount;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return this.status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getDateApply() {
        return this.dateApply;
    }

    public void setDateApply(LocalDate dateApply) {
        this.dateApply = dateApply;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    

    
    
}
