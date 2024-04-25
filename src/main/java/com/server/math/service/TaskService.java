package com.server.math.service;

import com.server.math.dto.Quiz;
import com.server.math.dto.RandomTask;
import com.server.math.dto.Subject;
import com.server.math.model.Article;
import com.server.math.model.questions.Answer;
import com.server.math.model.questions.Task;
import com.server.math.repository.ArticleRepository;
import com.server.math.repository.TaskRepository;
import com.server.math.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    private ArticleRepository articleRepository;

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

    public RandomTask getRandomTask(Subject subject) {
        int classNumber;
        int day = getRandomNumberUsingInts(11, 17 + 1);

        if (subject.equals(Subject.PHYS)) {
            classNumber = getRandomNumberUsingInts(7, 11 + 1);
        } else {
            classNumber = getRandomNumberUsingInts(5, 11 + 1);
        }
        System.out.println("classNumber: " + classNumber);
        System.out.println("day: " + day);

        List<Task> tasks = taskRepository.findByClassNumberAndDayAndSubjectAndIsQuiz(classNumber, day, subject, false);
        List<Task> quiz = taskRepository.findByClassNumberAndDayAndSubjectAndIsQuiz(classNumber, day, subject, true);
        Article article = articleRepository.findByClassNumberAndDayAndSubject(classNumber, day, subject).get(0);
        return new RandomTask(article, quiz, tasks);
    }

    public List<Task> getAllTaskByClassAndDate(int classNumber, int date) {
        return taskRepository.findByClassNumberAndDay(classNumber, date);
    }

    public List<Task> getAllTaskByClassAndDateAndDifficultyAndSubject(int classNumber, int day, int difficultyLevel, Subject subject) {
        return taskRepository.findByClassNumberAndDayAndDifficultyLevelAndSubject(classNumber, day, difficultyLevel, subject);
    }

    public static int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }


}
