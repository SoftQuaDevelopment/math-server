package com.server.math.service;

import com.server.math.dto.LeadBoard;
import com.server.math.dto.Subject;
import com.server.math.model.Student;
import com.server.math.model.StudentAnswers;
import com.server.math.repository.StudentAnswersRepository;
import com.server.math.repository.StudentRepository;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeadBoardService {

    @Autowired
    private StudentAnswersRepository studentAnswersRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<LeadBoard> getLeadBordBySubjectAndClassNumberAndClassLetter(int classNumber, String classLetter, Subject subject) {
        List<StudentAnswers> studentAnswersList = studentAnswersRepository.findByStudent_ClassNumberAndStudent_ClassLetterAndTask_IsQuizFalseAndTask_Subject(classNumber, classLetter, subject);
        List<Student> studentList = studentRepository.findByClassNumberAndClassLetter(classNumber, classLetter);
        return getLeadBoards(studentAnswersList, studentList);
    }

    public List<LeadBoard> getLeadBoardByClassNumberAndClassLetter(int classNumber, String classLetter) {
        List<StudentAnswers> studentAnswersList = studentAnswersRepository.findByStudent_ClassNumberAndStudent_ClassLetterAndTask_IsQuizFalse(classNumber, classLetter);
        List<Student> studentList = studentRepository.findByClassNumberAndClassLetter(classNumber, classLetter);
        return getLeadBoards(studentAnswersList, studentList);
    }

    public List<LeadBoard> getLeadBordBySubjectAndClassNumber(int classNumber, Subject subject) {
        List<StudentAnswers> studentAnswersList = studentAnswersRepository.findByStudent_ClassNumberAndTask_IsQuizFalseAndTask_Subject(classNumber, subject);
        List<Student> studentList = studentRepository.findByClassNumber(classNumber);
        return getLeadBoards(studentAnswersList, studentList);
    }

    public List<LeadBoard> getLeadBoardByClassNumber(int classNumber) {
        List<StudentAnswers> studentAnswersList = studentAnswersRepository.findByStudent_ClassNumberAndTask_IsQuizFalse(classNumber);
        List<Student> studentList = studentRepository.findByClassNumber(classNumber);
        return getLeadBoards(studentAnswersList, studentList);
    }

    private static List<LeadBoard> getLeadBoards(List<StudentAnswers> studentAnswersList, List<Student> studentList) {
        Map<Long, Integer> studentPointsMap = studentAnswersList.stream()
                .collect(Collectors.groupingBy(studentAnswer -> studentAnswer.getStudent().getId(), Collectors.summingInt(StudentAnswers::getPoints)));

        return studentList.stream()
                .map(student -> new LeadBoard(student, studentPointsMap.getOrDefault(student.getId(), 0)))
                .collect(Collectors.toList());
    }

}
