package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.service.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    private StudentRepository studentRepository;

    @Autowired
    com.example.demo.service.StudentRepository studenRepository;


    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    //look for automatically parent
    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/allStudents")
    public ResponseEntity<List<Student>> findAll() {
        log.info("получил все студентов");
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getStudent (@PathVariable int id ){
       Student student = studentRepository.findById(id);
       log.info("you are going to get student with id" + id);
       if (student == null){
           log.error("nobody was found");
           return new ResponseEntity<String> ("No sudent found with this " + id,HttpStatus.NOT_FOUND );
       }
       return new ResponseEntity<Student>(student,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    //Response Entity http ответ
    public ResponseEntity deleteObject (@PathVariable int id){
        log.info("you are going to delete the student " + id);
        //   studentService.deleteStudent(id);
        int numberOfStudents = studentRepository.deleteStudent(id);
        log.info("the student" + id + "was deleted");
        if (numberOfStudents!=0) {
            log.info("студент удален");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            log.error("не найден студент");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<String> createStudent(@RequestBody @Valid Student student) throws Exception {
        if (studentRepository.findById(student.getId()) != null) {
            log.info("you can't add new person");

            return new ResponseEntity<String>("существует" + student.getId(), HttpStatus.NOT_ACCEPTABLE);
        }
        if (studentRepository.findById(student.getId()) != null){
            throw new Exception("it already exists");
        }
        log.info("i added a new student with id =" + student.getId());
        studentRepository.addStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }


}





/*
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    private ResponseEntity<String> printValidError(Errors errors) {
        FieldError field = errors.getFieldErrors().get(0);
        return new ResponseEntity<>(field.getField() + ": " +
                field.getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll() {
        log.info("GET request for a list of patients");
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, students.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> find(@PathVariable Long id) {
        log.info("GET request for a patient with id " + id);
        try {
            return new ResponseEntity<>(studentService.find(id), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            log.info("Patient with id " + id + " not found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> addPatient(@RequestBody @Valid Student student, Errors errors) {
        log.info("POST request for creation " + student);
        if (errors.hasErrors()) {
            log.info("Patient not valid");
            return printValidError(errors);
        }
        Long patient_id = studentService.savePatient(student);
        log.info("Patient created with id " + patient_id);
        return ResponseEntity.ok("Patient created with id " + patient_id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        log.info("DELETE request for a patient with id " + id);
        int countDeleted = studentService.delete(id);
        if (countDeleted == 0) {
            log.info("Patient with id " + id + " not found");
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
        log.info("Patient removed successfully");
        return new ResponseEntity<>("Patient removed successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable Long id, @RequestBody @Valid Student student, Errors errors) {
        log.info("PUT request for update " + student);
        if (errors.hasErrors()) {
            log.info("Patient not valid");
            return printValidError(errors);
        }
        try {
            Long patient_id = studentService.put(id, student);
            log.info("Patient updated with id " + patient_id);
            return new ResponseEntity<>("Patient updated with id " + patient_id, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> editPatient(@PathVariable Long id, @RequestBody Student student) {
        log.info("PATCH request for change with " + student);
        try {
            studentService.patch(id, student);
            log.info("Successfully changed");
            return new ResponseEntity<>("Successfully changed", HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

 */

