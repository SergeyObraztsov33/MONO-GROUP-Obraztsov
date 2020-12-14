package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> findAll () {
        return studentRepository.findAll();
    }

    public Student findStudentById(Long id) {
        return studentRepository.findStudentById(id);
    }

    public Student saveStudent (Student student) {
        return studentRepository.save(student);
    }


}
