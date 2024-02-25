package com.server.math.dto.result_table;

import com.server.math.dto.Subject;
import com.server.math.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class ResultTable {


    Student student;
    Subject subject;
    int points;
    int mark;

}
