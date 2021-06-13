package org.acme.rest.json.repositories;

import java.time.LocalDate;
import java.time.Month;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.rest.json.entities.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
public class RepositoryStudentTest {

    // Execute these TESTS: ./mvnw -Dtest=RepositoryStudentTest test

    @Inject
    RepositoryStudent repo;

    @Test
    public void testAllStudent() {
        Assertions.assertThat(repo.allStudents()).hasSize(2);
    }

    @Test
    public void containsTest() {
        Assertions.assertThat(repo.allStudents().stream().anyMatch(s -> s.getName().equalsIgnoreCase("Mateo"))).isTrue();
        Assertions.assertThat(repo.allStudents().stream().anyMatch(s -> s.getName().equalsIgnoreCase("Will"))).isTrue();
    }

    @Test
    public void addStudentTest() {

        // revisiÓN: FALTA EL OBJETO UNIVERSITY
        repo.persist(new Student("Pedro", "Gimenez", LocalDate.of(1990, Month.DECEMBER, 17), "+34 687687878"));

        System.out.println(repo.allStudents());

        Assertions.assertThat(repo.allStudents()).hasSize(3);

        Assertions.assertThat(repo.allStudents().stream().anyMatch(f -> f.getName().equalsIgnoreCase("Pedro"))).isTrue();

        Assertions.assertThat(repo.allStudents().stream().anyMatch(f -> f.getSurname().equalsIgnoreCase("Gimenez"))).isTrue();

        Assertions.assertThat(repo.allStudents().stream().anyMatch(f -> f.getDateBirth().equals(LocalDate.parse("1990-12-17")))).isTrue();
        Assertions.assertThat(repo.allStudents().stream().anyMatch(f -> f.getPhone().equalsIgnoreCase("+34 687687878"))).isTrue();

        // handmade rollback debido antipatron ActiveRecord
        Student student = repo.find("name", "Pedro").firstResult();
        repo.delete(student);
        Assertions.assertThat(repo.count()).isEqualTo(2);
    }
    @Test
    public void removeTest(){
        repo.deleteByName("Will");
        Assertions.assertThat(repo.allStudents()).hasSize(1);
        Assertions.assertThat(repo.allStudents().stream().anyMatch(f -> f.getName().equalsIgnoreCase("Will"))).isFalse();

        // handmade rollback debido al antipatron ActiveRecord 
        // REVISIÓN FALTA EL OBJETO UNIVERSITY
        repo.persist(new Student("Will", "Smith", LocalDate.of(1999, Month.JUNE, 17), "+34 677878997"));
        Assertions.assertThat(repo.count()).isEqualTo(2);
    }

    @Test
    public void getFruitTest() {
        Assertions.assertThat(repo.findByNameOptional("Mateo")).get().hasFieldOrPropertyWithValue("name", "Mateo").hasFieldOrPropertyWithValue("dateBirth", LocalDate.parse("2005-06-05")).hasFieldOrPropertyWithValue("phone", "+34 666666666");

        // Test Student doesn't exist
        Assertions.assertThat(repo.findByNameOptional("Julio")).isEmpty();
    }

    
}
