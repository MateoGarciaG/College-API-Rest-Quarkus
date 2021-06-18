package org.acme.rest.json.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.rest.json.entities.Enrollment;
import org.acme.rest.json.entities.Student;
import org.acme.rest.json.entities.Tuition;
import org.acme.rest.json.entities.University;
import org.acme.rest.json.repositories.RepositoryEnrollment;
import org.acme.rest.json.repositories.RepositoryStudent;
import org.acme.rest.json.repositories.RepositoryTuition;
import org.acme.rest.json.repositories.RepositoryUniversity;




@ApplicationScoped
public class ServiceStudent {

    @Inject
    RepositoryStudent repoStudent;

    @Inject
    RepositoryTuition repoTuition;

    @Inject
    RepositoryEnrollment repoEnrollment;

    @Inject
    RepositoryUniversity repoUniversity;

    
    public ServiceStudent() {}

    // Get Set<>

    public Set<Student> setStudents() {

        return repoStudent.allStudents();
    }

    public Set<Tuition> setTuitions() {
        return repoTuition.allTuitions();
    }

    public Set<Enrollment> setEnrollments() {
        return repoEnrollment.allEnrollment();
    }

    public Set<University> setUniversities() {
        return repoUniversity.allUniversities();
    }

    // ********************************************

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

    public List<University> universitiesOrderByName() {
        return repoUniversity.listAllOrderedByName();
    }

    // ******************************************


    // Add Entity


    public Boolean add(Student student) {

        // Like it was said in repoStudentStudent, in this layer we can call directly the method persist() or other from Panache to manage the Entity inside the Persistence Context. But with the use of a repoStudentSITORY we need to insert in the parameter of Panache methods the Entity which is affect by the panache method

        Optional<University> universityFromRepo = repoUniversity.find("name", student.getUniversity().getName()).firstResultOptional();

        if(universityFromRepo.isPresent()) {


            // To avoid Null of the Data of University, add the rest of the Data of the University to this new Student
            student.setUniversity(universityFromRepo.get());

            // After persist the new Student
            repoStudent.persist(student);

        } else {
            return false;
        }

        return true;
    }

    // public Boolean addTuition(Tuition tuition) {

    //     Optional<Student> student = repoStudent.find("name", tuition.enrollment.getStudent().getName()).firstResultOptional();

    //     if(student.isPresent()) {
    //         repoTuition.persist(tuition);
    //     } else {
    //         // repoStudent.persist(enrollment.student);

    //         return false;

    //     }

    //     return true;
    // }

    public Boolean addEnrollment(Enrollment enrollment) {

        Optional<Enrollment> studentFromEnrollments = repoEnrollment.find("student.id", enrollment.student.getId()).firstResultOptional();

        Optional<Student> studentFromStudents = repoStudent.findByIdOptional(enrollment.student.getId());
        

        // If Student if doesn't in Enrollment Table but the student exists:
        if(!studentFromEnrollments.isPresent() && studentFromStudents.isPresent()) {

            enrollment.student = studentFromStudents.get();

            // The tuition data from JSON it's add when the Enrollment is persist(), so we don't need to override this data of Tuition.

            // Add the new Tuition to Tuition Table
            repoTuition.persist(enrollment.tuition);

        } else {

            // If the student It's present in Enrollment Table it mean that already has a Tuition, so Return False
            return false;
            
        }

        // if the student exists and doesn't have a Tuition, persist the Enrollment
        repoEnrollment.persist(enrollment);

        


        return true;

    }


    public Boolean addUniversity(University university) {

        
        // Optional<University> universityFromRepo = repoUniversity.findByIdOptional(university.getId());

        Optional<University> universityFromRepo = repoUniversity.find("name", university.getName()).firstResultOptional();


        if(!universityFromRepo.isPresent()) {

            // If it's not exist the university before, Add to persistence context
            repoUniversity.persist(university);

        } else {
            return false;
        }

        return true;
    }


    // **********************************************+

    // Delete Entity

    public void remove(String name) {

        repoStudent.deleteByName(name);

    }

    public void removeTuition(Long id) {
        repoTuition.deleteByIdTuition(id);
    }

    public Boolean removeEnrollment(Enrollment enrollment) {

        Optional<Enrollment> studentFromEnrollments = repoEnrollment.find("student.id", enrollment.student.getId()).firstResultOptional();

        if(studentFromEnrollments.isPresent()) {
            // repoEnrollment.deleteByIdEnrollment(enrollment.getId());

            repoEnrollment.deleteByStudentId(enrollment.student.getId());

            repoTuition.deleteByIdTuition(enrollment.tuition.getId());
        } else {
            return false;
        }

        return true;
    }

    public boolean removeUniversity(University university) {

        Optional<University> universityFromRepo = repoUniversity.find("name", university.getName()).firstResultOptional();

        if(universityFromRepo.isPresent()) {
            repoUniversity.deleteByIdUniversity(university.getName());
        } else {
            return false;
        }

        return true;
    }


    // **********************************************

    // Get Filter by

    public Optional<Student> getStudentByName(String name) {
        
        return name.isBlank() ? Optional.ofNullable(null) : repoStudent.findByNameOptional(name);
    }

    public Optional<Tuition> getTuitionById(Long id) {
        return repoTuition.findByIdOptional(id);
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        return repoEnrollment.findByIdOptional(id);
    }

    public Optional<University> getUniversityById(String idName) {
        return repoUniversity.find("name", idName).firstResultOptional();
    }

    // ****************************************************


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


    // Update Tuition through Enrollment
    public Boolean updateEnrollment(Enrollment enrollment) {

        Optional<Enrollment> studentFromEnrollments = repoEnrollment.find("student.id", enrollment.student.getId()).firstResultOptional();

        Optional<Tuition> tuitionFromTuitions = repoTuition.findByIdOptional(enrollment.tuition.getId());

        // Optional<Student> studentFromStudents = repoStudent.findByIdOptional(enrollment.student.getId());
        

        // If Student exists in Enrollment Table:
        if(studentFromEnrollments.isPresent() && tuitionFromTuitions.isPresent()) {

            // Update Enrollment

            Enrollment updateEnrollment = studentFromEnrollments.get();

            // The data of the student will not be updated because always will be the same

            // Update the old data of Tuition
            updateEnrollment.setTuition(enrollment.getTuition());


            // Update Tuition in Tuition table

            Tuition tuitionUpdate = tuitionFromTuitions.get();

            tuitionUpdate.setStatus(enrollment.tuition.getStatus());

            tuitionUpdate.setDateApply(enrollment.tuition.getDateApply());

            tuitionUpdate.setAmount(enrollment.tuition.getAmount());

            // Update the tuition of the student in the Tuition Table, because in the Enrollment table will be updated too in the line before: return true
            repoTuition.persist(tuitionUpdate);

        } else {

            // If the student It's not present in Enrollment Table it mean that it was not added yet, so you must add first the Student with his/her Tuition in Enrollment Table before trying to update it, so Return False
            return false;
            
        }

        // if the student exists and doesn't have a Tuition, persist the Enrollment
        // ERROR DUPLICADO: Al parecer con Tablas PUentes no es necesario volver a Persistir la Entidad actualizada, ya con actualizarla basta para que Panache lo actualice.
        // SOlution: It seems that Panache automatically SAved all changes when the Entity is updated without call PERSIST() again:
        /* 
        "note that once persisted, you don't need to explicitly save your entity: all modifications are automatically persisted on transaction commit."
        */
        // repoEnrollment.persist(enrollment);


        return true;

    }


    public Optional<University> updateUniversity(University newUniversity) {
        
        // Optional<University> universityFromRepo = repoUniversity.find("name", newUniversity.getName()).firstResultOptional();

        Optional<University> universityFromRepo = repoUniversity.find("name", newUniversity.getName()).firstResultOptional();

        if(universityFromRepo.isPresent()) {
            University university = universityFromRepo.get();

            university.setAddress(newUniversity.getAddress());
            university.setEmail(newUniversity.getEmail());
            university.setPhone(newUniversity.getPhone());

            // We need to put in the parameter the Entity updated to persist it
            repoUniversity.persist(university);

        } else {
            return Optional.ofNullable(null);
        }

        return universityFromRepo;
    }



}
