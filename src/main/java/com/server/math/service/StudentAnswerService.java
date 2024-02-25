package com.server.math.service;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.model.questions.Answer;
import com.server.math.model.questions.CustomStudentAnswer;
import com.server.math.model.questions.Task;
import com.server.math.repository.*;
import com.server.math.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentAnswerService {

    @Autowired
    private StudentAnswersRepository studentAnswersRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CustomStudentAnswerRepository customStudentAnswerRepository;

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
        studentAnswers.setCustomStudentAnswer(null);
        studentAnswers.setPoints(0);

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task id not found!"));
        studentAnswers.setTask(task);

        return studentAnswersRepository.save(studentAnswers);
    }

    public List<StudentAnswers> assignTasksToAllStudentsInClassNumber(Long taskId, int classNumber) {
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task id not found!"));
        List<Student> studentList = studentRepository.findByClassNumber(classNumber);

        List<StudentAnswers> studentAnswersList = studentList.stream()
                .map(student -> {
                    StudentAnswers studentAnswers = new StudentAnswers();
                    studentAnswers.setStudent(student);
                    studentAnswers.setTask(task);
                    studentAnswers.setAnswer(null);
                    studentAnswers.setCustomStudentAnswer(null);
                    studentAnswers.setPoints(0);
                    return studentAnswers;
                }).collect(Collectors.toList());

        return studentAnswersRepository.saveAll(studentAnswersList);
    }

    public ObjectMessageResponse<?> setStudentEasyAnswer(Long studentAnswerId, Long studentTelegramId, Long taskId) {
        Student student = studentRepository
                .findByTelegramId(studentTelegramId)
                .orElseThrow(() -> new ResourceNotFoundException("Student does not exist!"));

        if(student.isBan())
            return new ObjectMessageResponse<>("A banned student cannot answer questions", "Student is banned");

        Answer studentAnswer = answerRepository.findById(studentAnswerId).orElseThrow(() -> new ResourceNotFoundException("Answer does not exist!"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task does not exist!"));

        Set<Answer> correctAnswers = task.getAnswers();
        int points = correctAnswers.stream()
                .filter(answer -> Objects.equals(answer.getId(), studentAnswer.getId()))
                .filter(answer -> studentAnswer.getIsCorrect())
                .mapToInt(answer -> getPoints(task))
                .findFirst()
                .orElse(0);

        int updateCount = studentAnswersRepository.updateAnswerAndPointsByStudentAndTask(studentAnswer, points, student, task);
        return new ObjectMessageResponse<>("Update completed", updateCount);
    }

    public ObjectMessageResponse<?> setStudentCustomAnswer(Long studentTelegramId, Long taskId, String studentAnswer) {
        Student student = studentRepository
                .findByTelegramId(studentTelegramId)
                .orElseThrow(() -> new ResourceNotFoundException("Student does not exist!"));

        if(student.isBan())
            return new ObjectMessageResponse<>("A banned student cannot answer questions", "Student is banned");

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task does not exist!"));

        CustomStudentAnswer customAnswer = getCustomStudentAnswer(studentAnswer, task, student);
        int points = 0;
        if (customAnswer.isCorrect()) {
            points = getPoints(task);
        }

        CustomStudentAnswer _answer = customStudentAnswerRepository.save(customAnswer);

        int updateCount = studentAnswersRepository.updateCustomStudentAnswerAndPointsByStudentAndTask(_answer, points, student, task);
        return new ObjectMessageResponse<>("Update completed", updateCount);
    }

    private static CustomStudentAnswer getCustomStudentAnswer(String studentAnswer, Task task, Student student) {
        Answer correctAnswer = task.getAnswers().stream()
                .filter(Answer::getIsCorrect)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No correct answer found"));

        System.out.println(correctAnswer);
        assert correctAnswer != null;

        CustomStudentAnswer customAnswer = new CustomStudentAnswer();
        customAnswer.setStudentAnswerText(studentAnswer);
        customAnswer.setCorrect(Objects.equals(correctAnswer.getText(), studentAnswer));
        customAnswer.setTask(task);
        customAnswer.setStudent(student);
        return customAnswer;
    }

    private static int getPoints(Task task) {
        int points = 0;
        if (task.getDifficultyLevel() == 1) {
            points = 1;
        }
        if (task.getDifficultyLevel() == 2) {
            points = 2;
        }
        if (task.getDifficultyLevel() == 3) {
            points = 3;
        }
        if (task.getDifficultyLevel() == 4) {
            points = 7;
        }
        return points;
    }

}
