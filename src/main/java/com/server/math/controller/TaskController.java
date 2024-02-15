package com.server.math.controller;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.model.questions.Answer;
import com.server.math.model.questions.Task;
import com.server.math.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task.create")
    private ResponseEntity<?> createTask(@RequestBody Task task) {
        Task _task = taskService.createTask(task);
        return new ResponseEntity<>(_task, HttpStatus.CREATED);
    }

    @PostMapping("/task.assignToStudent")
    private ResponseEntity<?> assignTaskStudent(@RequestParam(name = "studentId") Long studentId,
                                                 @RequestParam(name = "taskId") Long taskId) {
        StudentAnswers studentAnswers = taskService.assignTasksStudent(studentId, taskId);
        return new ResponseEntity<>(studentAnswers, HttpStatus.CREATED);
    }

    @PostMapping("/task.setStudentAnswer")
    private ResponseEntity<?> setStudentAnswer(@RequestParam(name = "answerId") Long answerId,
                                               @RequestParam(name = "points") int points,
                                               @RequestParam(name = "studentTelegramId") Long studentTelegramId,
                                               @RequestParam(name = "taskId") Long taskId) {
        int id = taskService.setStudentAnswer(answerId, points, studentTelegramId, taskId);
        return new ResponseEntity<>("" + id, HttpStatus.CREATED);
    }


}
