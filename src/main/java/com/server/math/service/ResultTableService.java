package com.server.math.service;

import com.server.math.dto.Subject;
import com.server.math.dto.result_table.Result;
import com.server.math.dto.result_table.ResultTable;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.model.questions.Task;
import com.server.math.repository.StudentAnswersRepository;
import com.server.math.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResultTableService {

    @Autowired
    private StudentAnswersRepository studentAnswersRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Page<ResultTable> generateTable(int pageNumber, int pageSize) {
        Page<StudentAnswers> studentAnswersList = studentAnswersRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<Student> studentList = studentRepository.findAll();

        List<Result> getPointsAndSubject = getPointsAndSubject(studentAnswersList, studentList);

        List<ResultTable> resultTables = new ArrayList<>();
        for (Result result : getPointsAndSubject) {
            ResultTable resultTable = getResultTable(result);
            resultTables.add(resultTable);
        }

        return new PageImpl<>(resultTables);
    }

    private static ResultTable getResultTable(Result leadBoard) {
        Student student = leadBoard.getStudent();
        int points = leadBoard.getPoints();
        int mark = 0;
        Subject subject = leadBoard.getSubject();

        if (Objects.equals(student.getClassLetter(), "Т")) {
            if (points >= 27) mark = 5;
            else if (points >= 17) mark = 4;
        } else {
            if (points >= 21) mark = 5;
            else if (points >= 14) mark = 4;
        }

        return new ResultTable(student, subject, points, mark);
    }

    private static List<Result> getPointsAndSubject(Page<StudentAnswers> studentAnswersList, List<Student> studentList) {
        return studentList.stream()
                .map(student -> {
                    int points = studentAnswersList.stream()
                            .filter(studentAnswer -> Objects.equals(student.getId(), studentAnswer.getStudent().getId()))
                            .mapToInt(StudentAnswers::getPoints)
                            .sum();


                    Subject subject = studentAnswersList.stream()
                            .filter(studentAnswer -> Objects.equals(student.getId(), studentAnswer.getStudent().getId()))
                            .map(StudentAnswers::getTask)
                            .map(Task::getSubject)
                            .findFirst()
                            .orElse(null);

                    return new Result(student, subject, points);
                }).collect(Collectors.toList());
    }
}
