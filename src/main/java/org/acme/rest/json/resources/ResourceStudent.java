package org.acme.rest.json.resources;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.acme.rest.json.entities.Enrollment;
import org.acme.rest.json.entities.Student;
import org.acme.rest.json.entities.Tuition;
import org.acme.rest.json.service.ServiceStudent;

@Path("/api")
public class ResourceStudent {
    
    @Inject
    ServiceStudent service;

    public ResourceStudent() {
        // CDI
    }

    @GET
    @Path("/students/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response allStudents() {
        // ok() method already have a STATUS 200
        return Response.ok(service.setStudents(), MediaType.APPLICATION_JSON).header("message", "All students returned").build();
    }

    @POST
    @Path("/students/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addStudent(@Valid Student student) {
        service.add(student);

        return Response.ok(service.setStudents(), MediaType.APPLICATION_JSON_TYPE).header("message", "The Student was add succesfully!!!").build();
    }

    @DELETE
    @Path("/students/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteStudent(@Valid Student student) {
        service.remove(student.getName());

        return Response.ok(service.setStudents(), MediaType.APPLICATION_JSON).header("message", "The Student was deleted succesfully!!!").build();
    }

    
    @PUT
    @Path("/students/put")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateStudent(@Valid Student student) {
        Optional<Student> studentUpdated = service.updateStudent(student);

        return studentUpdated.isPresent() ? Response.status(Response.Status.OK).entity(studentUpdated.get()).build() : Response.status(Response.Status.NOT_FOUND).build();

    }

    @GET
    @Path("/students/{name}")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getByName(@PathParam("name") String name) {
        Optional<Student> student = service.getStudentByName(name);

        return student.isPresent() ? Response.status(Response.Status.OK).entity(student.get()).build() : Response.status(Response.Status.NOT_FOUND).build();

    }

    // *********************************************

    // Tuition Endpoints

    @GET
    @Path("/tuitions/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response allTuitions() {
        // ok() method already have a STATUS 200
        return Response.ok(service.setTuitions(), MediaType.APPLICATION_JSON).header("message", "All tuitions returned").build();
    }


    @GET
    @Path("/tuitions/{id}")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getTuitionById(@PathParam("id") Long id) {
        Optional<Tuition> tuition = service.getTuitionById(id);

        return tuition.isPresent() ? Response.status(Response.Status.OK).entity(tuition.get()).build() : Response.status(Response.Status.NOT_FOUND).build();

    }

    // @POST
    // @Path("/tuitions/add")
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // @Transactional
    // public Response addTuition(@Valid Student student) {
    //     service.add(student);

    //     return Response.ok(service.setStudents(), MediaType.APPLICATION_JSON_TYPE).header("message", "The Student was add succesfully!!!").build();
    // }



    // *********************************************

    // Enrollment Endpoints

    // GET

    @GET
    @Path("/enrollments/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response allEnrollments() {
        // ok() method already have a STATUS 200
        return Response.ok(service.setEnrollments(), MediaType.APPLICATION_JSON).header("message", "All Enrollments returned").build();
    }

    // POST


    @POST
    @Path("/enrollments/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    // {"student": 1050, "tuition": {"id": 1026, "status": true, "dateApply": "2019-10-17", "amount": 789.80} }
    public Response addEnrollment(@Valid Enrollment enrollment) {
        Boolean acceptEnrollment = service.addEnrollment(enrollment);

        // SI "acceptEnrolment" i'ts True it means that the Student exist so the Tuition can be add related to that student, if it doesn't exists, the tuition doesn't have a reason to exist, because a Tuition cannot be related to nobody, always is related to a student.
        return acceptEnrollment ? Response.ok(service.setEnrollments(), MediaType.APPLICATION_JSON_TYPE).header("message", "The Enrollment and Tuition was add succesfully!!!").build() : Response.status(Response.Status.NOT_FOUND).build();
    }


    @PUT
    @Path("/enrollments/put")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateEnrollment(@Valid Enrollment enrollment) {
        Boolean enrollmentUpdated = service.updateEnrollment(enrollment);

        return enrollmentUpdated ? Response.ok(service.setEnrollments(), MediaType.APPLICATION_JSON_TYPE).header("message", "The Enrollment and Tuition was updated succesfully!!!").build() : Response.status(Response.Status.NOT_FOUND).build();

    }


    @DELETE
    @Path("/enrollments/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteEnrollment(@Valid Enrollment enrollment) {


        Boolean checkRemoveEnrollment = service.removeEnrollment(enrollment);


        return checkRemoveEnrollment ? Response.ok(service.setEnrollments(), MediaType.APPLICATION_JSON).header("message", "The Enrollment and Tuition was remove was deleted succesfully!!!").build() : Response.status(Response.Status.NOT_FOUND).build();
    }



    @GET
    @Path("/enrollments/{id}")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getEnrollmentById(@PathParam("id") Long id) {
        Optional<Enrollment> enrollment = service.getEnrollmentById(id);

        return enrollment.isPresent() ? Response.status(Response.Status.OK).entity(enrollment.get()).build() : Response.status(Response.Status.NOT_FOUND).build();

    }



    // *********************************************


}
