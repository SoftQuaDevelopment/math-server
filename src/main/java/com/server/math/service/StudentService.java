package com.server.math.service;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.model.Student;
import com.server.math.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }



}
