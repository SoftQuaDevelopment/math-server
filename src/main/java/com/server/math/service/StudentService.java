package com.server.math.service;

import com.server.math.model.Student;
import com.server.math.repository.StudentRepository;
import com.server.math.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student getStudentByTelegramId(Long telegramId) {
        return studentRepository.findByTelegramId(telegramId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
    }




}
