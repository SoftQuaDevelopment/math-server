package com.server.math.dto;

import com.server.math.model.questions.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;


@Data
@AllArgsConstructor
public class Quiz {

    Long id;
    Integer classNumber;
    Integer day;
    Subject subject;
    String text;
    Set<Answer> answerList;

}
