package com.server.math.service;

import com.server.math.model.questions.CustomStudentAnswer;
import com.server.math.repository.CustomStudentAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomAnswerService {

    @Autowired
    private CustomStudentAnswerRepository customStudentAnswerRepository;

    public List<CustomStudentAnswer> getCustomAnswersByStudentTelegramId(Long telegramId) {
        return customStudentAnswerRepository.findByStudent_TelegramId(telegramId);
    }

    public List<CustomStudentAnswer> getCustomAnswersByTaskId(Long taskId) {
        return customStudentAnswerRepository.findByTask_Id(taskId);
    }

}
