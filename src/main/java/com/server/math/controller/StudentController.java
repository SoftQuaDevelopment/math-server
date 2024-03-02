package com.server.math.controller;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.dto.Subject;
import com.server.math.model.Student;
import com.server.math.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "student", description = "Student management")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @Operation(
            summary = "Create student",
            description = "Creating a user with a unique telegramId.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Student.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/student.create")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @Operation(
            summary = "Ban or unban student by telegramId",
            description = "For bad behavior the user gets banned.")
    @PostMapping("/student.ban")
    public ResponseEntity<?> setBanStudent(@RequestParam(name = "telegramId") Long telegramId,
                                           @RequestParam(name = "isBan") boolean isBan) {
        ObjectMessageResponse<?> objectMessageResponse = studentService.setBanStudent(telegramId, isBan);
        return new ResponseEntity<>(objectMessageResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve a Student by telegramId",
            description = "Get a Student object by specifying its telegram id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Student.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/student.get")
    public ResponseEntity<?> getStudentByTelegramId(@RequestParam(name = "telegramId") Long telegramId) {
        Student student = studentService.getStudentByTelegramId(telegramId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/student.getPointsBySubject")
    public ResponseEntity<?> getStudentPointsBySubject(@RequestParam(name = "telegramId") Long telegramId,
                                                       @RequestParam(name = "subject")Subject subject) {
        int points = studentService.getStudentPointsBySubject(telegramId, subject);
        ObjectMessageResponse<Integer> pointsMessage = new ObjectMessageResponse<>("points student", points );
        return new ResponseEntity<>(pointsMessage, HttpStatus.OK);
    }
    @GetMapping("/student.getPoints")
    public ResponseEntity<?> getStudentPoints(@RequestParam(name = "telegramId") Long telegramId) {
        int points = studentService.getStudentPoints(telegramId);
        ObjectMessageResponse<Integer> pointsMessage = new ObjectMessageResponse<>("points student", points );
        return new ResponseEntity<>(pointsMessage, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all users in classNumber",
            description = "Obtaining a list of students from the entire parallel class")
    @GetMapping("/student.getAllByClassNumber")
    public ResponseEntity<?> getAllStudentsByClassNumber(@RequestParam(name = "classNumber") int classNumber) {
        List<Student> students = studentService.getAllStudentsByClassNumber(classNumber);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user",
            description = "Delete user by telegramId")
    @DeleteMapping("/student.deleteByTelegramId")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "telegramId") Long telegramId) {
        return studentService.deleteUser(telegramId);
    }

}
