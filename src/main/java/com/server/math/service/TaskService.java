package com.server.math.service;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.dto.Subject;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.model.questions.Answer;
import com.server.math.model.questions.Task;
import com.server.math.repository.AnswerRepository;
import com.server.math.repository.StudentAnswersRepository;
import com.server.math.repository.StudentRepository;
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

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentAnswersRepository studentAnswersRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public Task createTask(Task task) {
        Set<Answer> answerSet = task.getAnswers();
        if (!answerSet.isEmpty()) {
            for (Answer answer : answerSet) {
                task.addAnswer(answer);
            }
        }

        return taskRepository.save(task);
    }

    public StudentAnswers assignTasksStudent(Long studentId, Long taskId) {
        StudentAnswers studentAnswers = new StudentAnswers();

        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found!")
                );
        studentAnswers.setStudent(student);
        studentAnswers.setAnswer(null);
        studentAnswers.setPoints(0);

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task id not found!"));
        studentAnswers.setTask(task);

        return studentAnswersRepository.save(studentAnswers);
    }

    public int setStudentAnswer(Long answerId, int points, Long studentTelegramId, Long taskId) {

        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer Not Found!"));
        Student student = studentRepository.findByTelegramId(studentTelegramId).orElseThrow(() -> new ResourceNotFoundException("Student Not Found!"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found!"));

        return studentAnswersRepository.updateAnswerAndPointsByStudentAndTask(answer, points, student, task);
    }

    public List<Task> getAllTaskByClassAndDate(byte classNumber, byte date) {
        return taskRepository.findByClassNumberAndDay(classNumber, date);
    }

    public List<Task> getAllTaskByClassAndDateAndDifficultyAndSubject(byte classNumber, byte day, byte difficultyLevel, Subject subject) {
        return taskRepository.findByClassNumberAndDayAndDifficultyLevelAndSubject(classNumber, day, difficultyLevel, subject);
    }


}
