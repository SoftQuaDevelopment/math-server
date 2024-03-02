package com.server.math.repository;

import com.server.math.dto.Subject;
import com.server.math.model.questions.CustomStudentAnswer;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.model.questions.Answer;
import com.server.math.model.questions.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentAnswersRepository extends JpaRepository<StudentAnswers, Long> {


    List<StudentAnswers> findByStudent_TelegramId(Long telegramId);

    @Transactional
    @Modifying
    @Query("update StudentAnswers s set s.answer = ?1, s.points = ?2 where s.student = ?3 and s.task = ?4")
    int updateAnswerAndPointsByStudentAndTask(Answer answer, int points, Student student, Task task);

    @Transactional
    @Modifying
    @Query("update StudentAnswers s set s.customStudentAnswer = ?1, s.points = ?2 where s.student = ?3 and s.task = ?4")
    int updateCustomStudentAnswerAndPointsByStudentAndTask(CustomStudentAnswer customStudentAnswer, int points, Student student, Task task);

    long countByTask_IsQuizFalseAndStudent_TelegramId(Long telegramId);

    long countByTask_IsQuizTrueAndStudent_TelegramId(Long telegramId);


    List<StudentAnswers> findByStudent_ClassNumberAndStudent_ClassLetterAndTask_IsQuizFalseAndTask_Subject(Integer classNumber, String classLetter, Subject subject);

    List<StudentAnswers> findByStudent_ClassNumberAndStudent_ClassLetterAndTask_IsQuizFalse(Integer classNumber, String classLetter);

    List<StudentAnswers> findByStudent_ClassNumberAndTask_IsQuizFalse(Integer classNumber);

    List<StudentAnswers> findByStudent_ClassNumberAndTask_IsQuizFalseAndTask_Subject(Integer classNumber, Subject subject);

    @Transactional
    @Modifying
    @Query("delete from StudentAnswers s where s.student = ?1")
    int deleteByStudent(Student student);

    List<StudentAnswers> findByStudent_TelegramIdAndTask_Subject(Long telegramId, Subject subject);
}