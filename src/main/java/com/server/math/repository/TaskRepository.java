package com.server.math.repository;

import com.server.math.dto.Subject;
import com.server.math.model.questions.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByClassNumber(int classNumber);

    List<Task> findByDay(int day);

    List<Task> findByClassNumberAndDay(int classNumber, int day);

    List<Task> findByClassNumberAndDayAndDifficultyLevelAndSubject(int classNumber, int day, int difficultyLevel, Subject subject);

    List<Task> findByIsQuizTrueAndSubject(Subject subject);

    List<Task> findByIsQuizTrueAndClassNumber(Integer classNumber);

    List<Task> findByIsQuizTrueAndDay(Integer day);

    List<Task> findByIsQuizTrueAndSubjectAndClassNumberAndDay(Subject subject, Integer classNumber, Integer day);
}