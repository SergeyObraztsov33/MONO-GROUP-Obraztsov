package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository <Student, Long> {

    List<Student> findAll();

    Student findStudentById(Long id);



}
