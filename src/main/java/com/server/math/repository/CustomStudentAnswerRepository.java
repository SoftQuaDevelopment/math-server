package com.server.math.repository;

import com.server.math.model.Student;
import com.server.math.model.questions.CustomStudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomStudentAnswerRepository extends JpaRepository<CustomStudentAnswer, Long> {
    List<CustomStudentAnswer> findByStudent_TelegramId(Long telegramId);

    List<CustomStudentAnswer> findByTask_Id(Long id);

    @Transactional
    @Modifying
    @Query("delete from CustomStudentAnswer c where c.student = ?1")
    int deleteByStudent(Student student);
}