package com.server.math.controller;

import com.server.math.dto.Quiz;
import com.server.math.dto.Subject;
import com.server.math.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.Temporal;
import java.util.List;

@RestController
public class QuizController {

    @Autowired
    QuizService quizService;

    @GetMapping("/quiz.getBySubject")
    private ResponseEntity<?> getQuizzesBySubject(@RequestParam(name = "subject") Subject subject) {
        List<Quiz> quizzes = quizService.getQuizzesBySubject(subject);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/quiz.getByClassNumber")
    private ResponseEntity<?> getQuizzesByClassNumber(@RequestParam(name = "classNumber") int classNumber) {
        List<Quiz> quizzes = quizService.getQuizzesByClassNumber(classNumber);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/quiz.getByDay")
    private ResponseEntity<?> getQuizzesByDay(@RequestParam(name = "day") int day) {
        List<Quiz> quizzes = quizService.getQuizzesByDay(day);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/quiz.getBySubjectAndClassNumberAndDay")
    private ResponseEntity<?> getQuizzesBySubjectAndClassNumberAndDay(Subject subject, int classNumber, int day) {
        List<Quiz> quizzes = quizService.getQuizzesBySubjectAndClassNumberAndDay(subject, classNumber, day);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

}
