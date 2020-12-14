package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> find (@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        Long student_id = studentService.saveStudent(student).getId();
        log.info ("you created student with id = " + student_id);
        return ResponseEntity.ok("you created with id+  " + student_id);
    }
     /*
    @DeleteMapping("/{id}")
    public String delete (@PathVariable Long id) {
        Student student =  studentService.findStudentById(id);
        if (student.isPresent()) {
            student
        }
    }

      */
}

