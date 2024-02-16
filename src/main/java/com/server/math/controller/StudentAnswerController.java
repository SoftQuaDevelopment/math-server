package com.server.math.controller;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.model.StudentAnswers;
import com.server.math.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentAnswerController {

    @Autowired
    StudentAnswerService studentAnswerService;

    @PostMapping("/answer.assignToStudent")
    private ResponseEntity<?> assignTaskStudent(@RequestParam(name = "studentTelegramId") Long studentTelegramId,
                                                @RequestParam(name = "taskId") Long taskId) {
        StudentAnswers studentAnswers = studentAnswerService.assignTasksStudent(studentTelegramId, taskId);
        return new ResponseEntity<>(studentAnswers, HttpStatus.CREATED);
    }

    @PostMapping("/answer.setStudentAnswer")
    private ResponseEntity<?> setStudentAnswer(@RequestParam(name = "answerId") Long answerId,
                                               @RequestParam(name = "points") int points,
                                               @RequestParam(name = "studentTelegramId") Long studentTelegramId,
                                               @RequestParam(name = "taskId") Long taskId) {
        int id = studentAnswerService.setStudentAnswer(answerId, points, studentTelegramId, taskId);
        return new ResponseEntity<>("" + id, HttpStatus.CREATED);
    }

    @GetMapping("/answer.getStudentAnswersByTelegramId")
    private ResponseEntity<?> getStudentAnswersByTelegramId(@RequestParam(name = "studentTelegramId") Long studentTelegramId) {
        List<StudentAnswers> studentAnswersList = studentAnswerService.getStudentAnswerByTelegramId(studentTelegramId);
        return new ResponseEntity<>(studentAnswersList, HttpStatus.OK);
    }

    @GetMapping("/answer.getCountTaskOrQuiz")
    private ResponseEntity<?> getStudentTaskOrQuizCount(@RequestParam(name = "studentTelegramId") Long studentTelegramId,
                                                        @RequestParam(name = "isQuiz") boolean isQuiz) {
        long count = isQuiz ? studentAnswerService.getQuizCount(studentTelegramId) : studentAnswerService.getTaskCount(studentTelegramId);
        ObjectMessageResponse<Long> countMessage = new ObjectMessageResponse<>("Count calculate!", count);
        return new ResponseEntity<>(countMessage, HttpStatus.OK);
    }

}
