package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    private static Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Student>>findAll () {
        List<Student> students = studentService.findAll();
        log.info("This is all your students" );
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> find (@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.ACCEPTED);
    }


    @PostMapping
    public ResponseEntity<Student> saveStudent(@RequestBody @Valid Student student){

        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(Student student){

        if (student == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       this.studentService.save(student);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Student> deleteStudent(Long id){
        Student student = this.studentService.findStudentById(id);

        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

