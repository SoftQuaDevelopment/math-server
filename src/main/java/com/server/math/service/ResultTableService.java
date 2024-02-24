package com.server.math.service;

import com.server.math.dto.LeadBoard;
import com.server.math.dto.ResultTable;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.repository.StudentAnswersRepository;
import com.server.math.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResultTableService {

    @Autowired
    private StudentAnswersRepository studentAnswersRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<ResultTable> generateTable(int pageNumber, int pageSize) {
        // todo: доделать вот этот позор

        Page<StudentAnswers> studentAnswersList = studentAnswersRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<Student> studentList = studentRepository.findAll();

        List<LeadBoard> getPoints = getPoints(studentAnswersList, studentList);

        List<ResultTable> resultTables = new ArrayList<>();
        for (LeadBoard leadBoard : getPoints) {
            Student student = leadBoard.getStudent();
            int points = leadBoard.getPoints();
            int mark = 0;

            if (Objects.equals(student.getClassLetter(), "Т")) {
                if (points >= 27) mark = 5;
                else if (points >= 17) mark = 4;
            } else {
                if (points >= 21) mark = 5;
                else if (points >= 14) mark = 4;
            }

            ResultTable resultTable = new ResultTable(student, null, points, mark);
        }

        return null;
    }

    private static List<LeadBoard> getPoints(Page<StudentAnswers> studentAnswersList, List<Student> studentList) {
        Map<Long, Integer> studentPointsMap = studentAnswersList.stream()
                .collect(Collectors.groupingBy(studentAnswer -> studentAnswer.getStudent().getId(),
                        Collectors.summingInt(StudentAnswers::getPoints)));

        return studentList.stream()
                .map(student -> new LeadBoard(student, studentPointsMap.getOrDefault(student.getId(), 0)))
                .collect(Collectors.toList());
    }

}
