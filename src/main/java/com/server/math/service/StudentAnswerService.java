package com.server.math.service;

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

@Service
public class StudentAnswerService {

    @Autowired
    StudentAnswersRepository studentAnswersRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public List<StudentAnswers> getStudentAnswerByTelegramId(Long telegramId) {
        return studentAnswersRepository.findByStudent_TelegramId(telegramId);
    }

    public long getTaskCount(Long telegramId) {
        return studentAnswersRepository.countByTask_IsQuizFalseAndStudent_TelegramId(telegramId);
    }

    public long getQuizCount(Long telegramId) {
        return studentAnswersRepository.countByTask_IsQuizTrueAndStudent_TelegramId(telegramId);
    }

    public StudentAnswers assignTasksStudent(Long studentTelegramId, Long taskId) {
        StudentAnswers studentAnswers = new StudentAnswers();

        Student student = studentRepository
                .findByTelegramId(studentTelegramId)
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

        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer does not exist!"));
        Student student = studentRepository.findByTelegramId(studentTelegramId).orElseThrow(() -> new ResourceNotFoundException("Student does not exist!"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task does not exist!"));

        return studentAnswersRepository.updateAnswerAndPointsByStudentAndTask(answer, points, student, task);
    }

}
