package com.server.math.controller;

import com.server.math.dto.CountAssignedTasks;
import com.server.math.dto.ObjectMessageResponse;
import com.server.math.model.StudentAnswers;
import com.server.math.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/answer.assignToStudentsByClassNumber")
    private ResponseEntity<?> assignTasksToAllStudentsInClassNumber(@RequestParam(name = "taskId") Long taskId,
                                                                    @RequestParam(name =  "classNumber") int classNumber) {
        List<StudentAnswers> studentAnswersList = studentAnswerService.assignTasksToAllStudentsInClassNumber(taskId, classNumber);
        int countAssignedAnswers = studentAnswersList.size();
        CountAssignedTasks<List<StudentAnswers>> assignedTasks = new CountAssignedTasks<>(countAssignedAnswers, studentAnswersList);
        return new ResponseEntity<>(assignedTasks, HttpStatus.CREATED);
    }

    @PostMapping("/answer.setStudentEasyAnswer")
    private ResponseEntity<?> setStudentEasyAnswer(@RequestParam(name = "studentTelegramId") Long studentTelegramId,
                                                   @RequestParam(name = "taskId") Long taskId,
                                                   @RequestParam(name = "studentAnswerId") Long answerId) {
        ObjectMessageResponse<?> messageResponse = studentAnswerService.setStudentEasyAnswer(answerId, studentTelegramId, taskId);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PostMapping("/answer.setStudentCustomAnswer")
    private ResponseEntity<?> setStudentCustomAnswer(@RequestParam(name = "studentTelegramId") Long studentTelegramId,
                                                     @RequestParam(name = "taskId") Long taskId,
                                                     @RequestParam(name = "CustomStudentAnswer") String customStudentAnswer) {
        ObjectMessageResponse<?> messageResponse = studentAnswerService.setStudentCustomAnswer(studentTelegramId, taskId, customStudentAnswer);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
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
