package com.server.math.service;


import com.server.math.dto.LeadBoard;
import com.server.math.dto.ResultTable;
import com.server.math.dto.Subject;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.repository.StudentAnswersRepository;
import com.server.math.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ResultTableService {

    @Autowired
    private StudentAnswersRepository studentAnswersRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<ResultTable> getResultBySubject(Subject subject) {
        List<Student> students = studentRepository.findAll();
        List<StudentAnswers> studentAnswers = studentAnswersRepository.findAll();

        return students.stream()
                .map(student -> {
                    int points = studentAnswers.stream()
                            .filter(studentAnswer -> student.getId().equals(studentAnswer.getStudent().getId())
                                    && studentAnswer.getTask().getSubject() == subject)
                            .mapToInt(StudentAnswers::getPoints)
                            .sum();

                    int mark = calculateMark(student, points);

                    return new ResultTable(student, subject, points, mark);
                })
                .collect(Collectors.toList());
    }

    private int calculateMark(Student student, int points) {
        if (student.getClassLetter().equals("Т")) {
            return points >= 27 ? 5 : (points >= 17 ? 4 : 0);
        } else {
            return points >= 21 ? 5 : (points >= 14 ? 4 : 0);
        }
    }


}
