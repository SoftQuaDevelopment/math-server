package com.server.math.repository;

import com.server.math.dto.Subject;
import com.server.math.model.questions.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByClassNumber(byte classNumber);

    List<Task> findByDay(byte day);

    List<Task> findByClassNumberAndDay(byte classNumber, byte day);

    List<Task> findByClassNumberAndDayAndDifficultyLevelAndSubject(byte classNumber, byte day, byte difficultyLevel, Subject subject);
}