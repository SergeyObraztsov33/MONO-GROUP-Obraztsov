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






