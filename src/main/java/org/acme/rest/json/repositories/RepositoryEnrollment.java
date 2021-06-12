package org.acme.rest.json.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import org.acme.rest.json.entities.Enrollment;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class RepositoryEnrollment implements PanacheRepository<Enrollment> {

    // There is not need to create a method to call panache methods because Repository Enrollment implements PanacheRepository already have this methods lika persist() which can be used and called directly in the layer Service

    // It's also not neccesary called the constructor to let this class become a CDI because PanacheRepository will be managed.

    public Set<Enrollment> allEnrollment() {
        Stream<Enrollment> enrollmentStream = this.streamAll();

        return enrollmentStream.collect(Collectors.toSet());
    }

    public List<Enrollment> listAllOrderedById() {
        return this.listAll(Sort.by("id").ascending());
    }

    public void deleteByIdEnrollment(Long id) {
        Optional<Enrollment> enrollment = this.findByIdOptional(id);
        if (enrollment.isPresent()) {
            this.delete(enrollment.get());
        }
    }

    public void deleteByStudentId(Long id) {
        Optional<Enrollment> enrollment = this.find("student.id", id).firstResultOptional();

        if(enrollment.isPresent()) {
            this.delete(enrollment.get());
        }

    }


}


