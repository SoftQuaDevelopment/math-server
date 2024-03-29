package com.server.math.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.math.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class LeadBoard {

    Student student;
    int points;

}
