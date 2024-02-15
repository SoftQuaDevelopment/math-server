package com.server.math.service;

import com.server.math.dto.Quiz;
import com.server.math.dto.Subject;
import com.server.math.model.questions.Task;
import com.server.math.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    TaskRepository taskRepository;

    public List<Quiz> getQuizzesBySubject(Subject subject) {
        List<Task> tasks = taskRepository.findByIsQuizTrueAndSubject(subject);
        return getQuizzes(tasks);
    }

    public List<Quiz> getQuizzesByClassNumber(int classNumber) {
        List<Task> tasks = taskRepository.findByIsQuizTrueAndClassNumber(classNumber);
        return getQuizzes(tasks);
    }

    public List<Quiz> getQuizzesByDay(int day) {
        List<Task> tasks = taskRepository.findByIsQuizTrueAndDay(day);
        return getQuizzes(tasks);
    }

    public List<Quiz> getQuizzesBySubjectAndClassNumberAndDay(Subject subject, int classNumber, int day) {
        List<Task> tasks = taskRepository.findByIsQuizTrueAndSubjectAndClassNumberAndDay(subject, classNumber, day);
        return getQuizzes(tasks);
    }

    private List<Quiz> getQuizzes(List<Task> tasks) {
        List<Quiz> quizzes = new ArrayList<>();
        for (Task task : tasks) {
            quizzes.add(
                    new Quiz(task.getId(),
                            task.getClassNumber(),
                            task.getDay(),
                            task.getSubject(),
                            task.getText(),
                            task.getAnswers())
            );
        }
        return quizzes;
    }
}
