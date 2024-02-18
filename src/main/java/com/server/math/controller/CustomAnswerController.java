package com.server.math.controller;

import com.server.math.model.questions.CustomStudentAnswer;
import com.server.math.service.CustomAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomAnswerController {

    @Autowired
    CustomAnswerService customAnswerService;

    @GetMapping("/customAnswers.getByStudentTelegramId")
    private ResponseEntity<?> getCustomAnswersByStudentTelegramId(@RequestParam(name = "telegramId") Long telegramId) {
        List<CustomStudentAnswer> customAnswerServices = customAnswerService.getCustomAnswersByStudentTelegramId(telegramId);
        return new ResponseEntity<>(customAnswerServices, HttpStatus.OK);
    }

    @GetMapping("/customAnswers.getByTaskId")
    private ResponseEntity<?> getCustomAnswersByTaskId(@RequestParam(name = "taskId") Long taskId) {
        List<CustomStudentAnswer> customStudentAnswers = customAnswerService.getCustomAnswersByTaskId(taskId);
        return new ResponseEntity<>(customStudentAnswers, HttpStatus.OK);
    }
}
