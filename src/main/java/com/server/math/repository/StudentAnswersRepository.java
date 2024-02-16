package com.server.math.repository;

import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.model.questions.Answer;
import com.server.math.model.questions.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentAnswersRepository extends JpaRepository<StudentAnswers, Long> {


    @Transactional
    @Modifying
    @Query("update StudentAnswers s set s.answer = ?1, s.points = ?2 where s.student = ?3 and s.task = ?4")
    int updateAnswerAndPointsByStudentAndTask(Answer answer, int points, Student student, Task task);

    List<StudentAnswers> findByStudent_TelegramId(Long telegramId);

    long countByTask_IsQuizFalseAndStudent_TelegramId(Long telegramId);

    long countByTask_IsQuizTrueAndStudent_TelegramId(Long telegramId);
}