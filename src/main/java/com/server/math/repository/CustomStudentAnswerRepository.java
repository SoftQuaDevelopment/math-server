package com.server.math.repository;

import com.server.math.model.questions.CustomStudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomStudentAnswerRepository extends JpaRepository<CustomStudentAnswer, Long> {
    List<CustomStudentAnswer> findByStudent_TelegramId(Long telegramId);

    List<CustomStudentAnswer> findByTask_Id(Long id);
}