package org.acme.rest.json.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import org.acme.rest.json.entities.Tuition;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class RepositoryTuition implements PanacheRepository<Tuition> {

    // There is not need to create a method to call panache methods because Repository Tuition implements PanacheRepository already have this methods lika persist() which can be used and called directly in the layer Service

    // It's also not neccesary called the constructor to let this class become a CDI because PanacheRepository will be managed.

    public Set<Tuition> allTuitions() {
        Stream<Tuition> tuitionStream = this.streamAll();

        return tuitionStream.collect(Collectors.toSet());
    }

    public List<Tuition> listAllOrderedByDate() {
        return this.listAll(Sort.by("dateApply").ascending());
    }

    public void deleteByIdTuition(Long id) {
        Optional<Tuition> tuition = this.findByIdOptional(id);
        if (tuition.isPresent()) {
            this.delete(tuition.get());
        }
    }

}