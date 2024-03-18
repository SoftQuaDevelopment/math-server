package com.server.math.dto;

import com.server.math.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultTable {

    Student student;
    Subject subject;
    int points;
    int mark;

}
