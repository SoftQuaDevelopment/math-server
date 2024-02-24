package com.server.math.controller;

import com.server.math.dto.Subject;
import com.server.math.model.questions.Task;
import com.server.math.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/task.get")
    private ResponseEntity<?> getTaskById(@RequestParam(name = "taskId") Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/task.getByClassAndDate")
    private ResponseEntity<?> getAllTaskByClassAndDate(@RequestParam(name = "classNumber") int classNumber,
                                                       @RequestParam(name = "day") int day) {
        List<Task> tasks = taskService.getAllTaskByClassAndDate(classNumber, day);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/task.getByClassAndDateAndDifficultyAndSubject")
    private ResponseEntity<?> getAllTaskByClassAndDateAndDifficultyAndSubject(@RequestParam(name = "classNumber") int classNumber,
                                                                              @RequestParam(name = "day") int day,
                                                                              @RequestParam(name = "difficultyLevel") int difficultyLevel,
                                                                              @RequestParam(name = "subject") Subject subject) {
        List<Task> tasks = taskService.getAllTaskByClassAndDateAndDifficultyAndSubject(classNumber, day, difficultyLevel, subject);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
