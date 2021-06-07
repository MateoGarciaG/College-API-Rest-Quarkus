package org.acme.rest.json.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.rest.json.entities.Enrollment;
import org.acme.rest.json.entities.Student;
import org.acme.rest.json.entities.Tuition;
import org.acme.rest.json.repositories.RepositoryEnrollment;
import org.acme.rest.json.repositories.RepositoryStudent;
import org.acme.rest.json.repositories.RepositoryTuition;




@ApplicationScoped
public class ServiceStudent {

    @Inject
    RepositoryStudent repoStudent;

    @Inject
    RepositoryTuition repoTuition;

    @Inject
    RepositoryEnrollment repoEnrollment;

    
    public ServiceStudent() {}

    // Get Set<>

    public Set<Student> setStudents() {

        return repoStudent.allStudents();
    }

    public Set<Tuition> setTuitions() {
        return repoTuition.allTuitions();
    }

    public Set<Enrollment> seEnrollments() {
        return repoEnrollment.allEnrollment();
    }

    // order by

    public List<Student> studentsOrderByName() {
        return repoStudent.listAllOrderedByName();
    }

    public List<Tuition> tuitionOrderByDate() {
        return repoTuition.listAllOrderedByDate();
    }

    public List<Enrollment> enrollmentsOrderById() {
        return repoEnrollment.listAllOrderedById();
    }


    // Add Entity


    public void add(Student student) {

        // Like it was said in repoStudentStudent, in this layer we can call directly the method persist() or other from Panache to manage the Entity inside the Persistence Context. But with the use of a repoStudentSITORY we need to insert in the parameter of Panache methods the Entity which is affect by the panache method
        repoStudent.persist(student);
    }

    public void addTuition(Tuition tuition) {
        repoTuition.persist(tuition);
    }

    public void addEnrollment(Enrollment enrollment) {
        Optional<Student> student = repoStudent.find("name", enrollment.student.getName()).firstResultOptional();

        if(student.isPresent()) {
            enrollment.student = student.get();
        } else {
            repoStudent.persist(enrollment.student);
        }

        Optional<Tuition> tuition = repoTuition.find("id", enrollment.tuition.getId()).firstResultOptional();

        if(tuition.isPresent()) {
            enrollment.tuition = tuition.get();
        } else {
            repoTuition.persist(enrollment.tuition);
        }

        repoEnrollment.persist(enrollment);
    }

    // Delete Entity

    public void remove(String name) {

        repoStudent.deleteByName(name);

    }

    public void removeTuition(Long id) {
        repoTuition.deleteByIdTuition(id);
    }

    public void removeEnrollment(Long id) {
        repoEnrollment.deleteByIdEnrollment(id);
    }

    // Get Filter by

    public Optional<Student> getStudentByName(String name) {
        
        return name.isBlank() ? Optional.ofNullable(null) : repoStudent.findByNameOptional(name);
    }

    public Optional<Tuition> getTuitionById(Long id) {
        return repoTuition.findByIdOptional(id);
    }

    public Optional<Enrollment> getEnrollmentByID(Long id) {
        return repoEnrollment.findByIdOptional(id);
    }

    


    // update Entity

    /* 
    https://stackoverflow.com/questions/797834/should-a-restful-put-operation-return-something
    */

    public Optional<Student> updateStudent(Student newStudent) {
        
        Optional<Student> studentOptional = repoStudent.find("name", newStudent.getName()).firstResultOptional();

        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setSurname(newStudent.getSurname());
            student.setDateBirth(newStudent.getDateBirth());
            student.setPhone(newStudent.getPhone());

            // We need to put in the parameter the Entity updated to persist it
            repoStudent.persist(student);

        } else {
            return Optional.ofNullable(null);
        }

        return studentOptional;
    }


}
