package com.server.math.service;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.dto.Subject;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.repository.CustomStudentAnswerRepository;
import com.server.math.repository.StudentAnswersRepository;
import com.server.math.repository.StudentRepository;
import com.server.math.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentAnswersRepository studentAnswersRepository;
    @Autowired
    CustomStudentAnswerRepository customStudentAnswerRepository;

    public ResponseEntity<?> createStudent(Student student) {
        String name = student.getName();
        String lastName = student.getLastName();
        String classLetter = student.getClassLetter();
        int classNumber = student.getClassNumber();

        boolean existUserByData = studentRepository
                .existsByNameAndLastNameAndClassLetterAndClassNumber(name, lastName, classLetter, classNumber);

        boolean existByTelegramId = studentRepository.existsByTelegramId(student.getTelegramId());

        if (existUserByData) {
            ObjectMessageResponse<?> studentMessage = new ObjectMessageResponse<>("User already created!", true);
            return new ResponseEntity<>(studentMessage, HttpStatus.BAD_REQUEST);
        }

        if (existByTelegramId) {
            ObjectMessageResponse<?> studentMessage = new ObjectMessageResponse<>("User with this telegramId already created!", "Error!");
            return new ResponseEntity<>(studentMessage, HttpStatus.BAD_REQUEST);
        }

        student.setBan(false);
        Student _student = studentRepository.save(student);
        return new ResponseEntity<>(_student, HttpStatus.CREATED);
    }

    public ObjectMessageResponse<?> setBanStudent(Long telegramId, boolean isBan) {
        Student student = studentRepository
                .findByTelegramId(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found!"));
        student.setBan(isBan);

        studentRepository.updateIsBanByTelegramId(isBan, telegramId);
        return new ObjectMessageResponse<>("Student banned!", student);
    }

    public ObjectMessageResponse<?> getStudentIsBan(Long telegramId) {
        Student student = studentRepository
                .findByTelegramId(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not Found!"));

        boolean isBan = student.isBan();

        return new ObjectMessageResponse<>("Student ban status: ", isBan);
    }

    public int getStudentPointsBySubject(Long telegramId, Subject subject) {
        List<StudentAnswers> studentAnswers = studentAnswersRepository
                .findByStudent_TelegramIdAndTask_Subject(telegramId, subject);

        return studentAnswers.stream()
                .filter(studentAnswer -> Objects.equals(studentAnswer.getStudent().getId(), telegramId))
                .mapToInt(StudentAnswers::getPoints)
                .sum();
    }

    public int getStudentPoints(Long telegramId) {
        List<StudentAnswers> studentAnswers = studentAnswersRepository.findByStudent_TelegramId(telegramId);

        return studentAnswers.stream()
                .filter( studentAnswer -> Objects.equals(studentAnswer.getStudent().getTelegramId(), telegramId))
                .mapToInt(StudentAnswers::getPoints)
                .sum();
    }


    public Student getStudentByTelegramId(Long telegramId) {
        return studentRepository.findByTelegramId(telegramId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
    }

    public List<Student> getAllStudentsByClassNumber(int classNumber) {
        return studentRepository.findByClassNumber(classNumber);
    }

    public ResponseEntity<?> deleteUser(Long telegramId) {
        Student student = studentRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found!"));

        if (!student.isBan()) {
            ObjectMessageResponse<String> objectMessageResponse = new ObjectMessageResponse<>("Only banned students can be deleted", "Ban status: false");
            return new ResponseEntity<>(objectMessageResponse, HttpStatus.BAD_REQUEST);
        }
        studentAnswersRepository.deleteByStudent(student);
        customStudentAnswerRepository.deleteByStudent(student);
        studentRepository.delete(student);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

}
