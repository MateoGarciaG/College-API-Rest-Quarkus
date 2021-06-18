package org.acme.rest.json.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import org.acme.rest.json.entities.Tuition;
import org.acme.rest.json.entities.University;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class RepositoryUniversity implements PanacheRepositoryBase<University, String>{
    
    public Set<University> allUniversities() {

        Stream<University> universitiesStream = this.streamAll();

        return universitiesStream.collect(Collectors.toSet());
    }

    public List<University> listAllOrderedByName() {
        return this.listAll(Sort.by("name").ascending());
    }

    // Delete Methods

    public void deleteByIdUniversity(String idName) {

        Optional<University> university = this.findByIdOptional(idName);

        if(university.isPresent()) {
            this.delete(university.get());
        }


    }

}
