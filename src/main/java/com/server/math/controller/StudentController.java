package com.server.math.controller;

import com.server.math.model.Student;
import com.server.math.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/student.create")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        Student _student = studentService.createStudent(student);
        return new ResponseEntity<>(_student, HttpStatus.CREATED);
    }

    @GetMapping("/student.get")
    public ResponseEntity<?> getStudentByTelegramId(@RequestParam("telegramId") Long telegramId) {
        Student student = studentService.getStudentByTelegramId(telegramId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }



}
