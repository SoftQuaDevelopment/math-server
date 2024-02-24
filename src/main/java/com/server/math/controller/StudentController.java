package com.server.math.controller;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.model.Student;
import com.server.math.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/student.create")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PostMapping("/student.ban")
    public ResponseEntity<?> setBanStudent(@RequestParam(name = "telegramId") Long telegramId,
                                           @RequestParam(name = "isBan") boolean isBan) {
        ObjectMessageResponse<?> objectMessageResponse = studentService.setBanStudent(telegramId, isBan);
        return new ResponseEntity<>(objectMessageResponse, HttpStatus.OK);
    }

    @GetMapping("/student.get")
    public ResponseEntity<?> getStudentByTelegramId(@RequestParam(name = "telegramId") Long telegramId) {
        Student student = studentService.getStudentByTelegramId(telegramId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/student.getAllByClassNumber")
    public ResponseEntity<?> getAllStudentsByClassNumber(@RequestParam(name = "classNumber") int classNumber) {
        List<Student> students = studentService.getAllStudentsByClassNumber(classNumber);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @DeleteMapping("/student.deleteByTelegramId")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "telegramId") Long telegramId) {
        Student student = studentService.deleteUser(telegramId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

}
