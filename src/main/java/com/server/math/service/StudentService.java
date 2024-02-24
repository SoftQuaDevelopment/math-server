package com.server.math.service;

import com.server.math.dto.ObjectMessageResponse;
import com.server.math.model.Student;
import com.server.math.repository.StudentRepository;
import com.server.math.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {


    @Autowired
    StudentRepository studentRepository;

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


    public Student getStudentByTelegramId(Long telegramId) {
        return studentRepository.findByTelegramId(telegramId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
    }

    public List<Student> getAllStudentsByClassNumber(int classNumber) {
        return studentRepository.findByClassNumber(classNumber);
    }

    public Student deleteUser(Long telegramId) {
        studentRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found!"));

        List<Student> students = studentRepository.deleteByTelegramId(telegramId);
        return students.get(0);
    }

}
