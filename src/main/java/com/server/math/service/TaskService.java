package com.server.math.service;

import com.server.math.dto.Subject;
import com.server.math.model.questions.Answer;
import com.server.math.model.questions.Task;
import com.server.math.repository.TaskRepository;
import com.server.math.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Task createTask(Task task) {
        Set<Answer> answerSet = task.getAnswers();
        if (!answerSet.isEmpty()) {
            for (Answer answer : answerSet) {
                task.addAnswer(answer);
            }
        }

        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task does not exist!"));
    }

    public List<Task> getAllTaskByClassAndDate(int classNumber, int date) {
        return taskRepository.findByClassNumberAndDay(classNumber, date);
    }

    public List<Task> getAllTaskByClassAndDateAndDifficultyAndSubject(int classNumber, int day, int difficultyLevel, Subject subject) {
        return taskRepository.findByClassNumberAndDayAndDifficultyLevelAndSubject(classNumber, day, difficultyLevel, subject);
    }


}
