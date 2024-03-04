package com.server.math.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class IsStudentAnswered<T> {

    Boolean isAnswered;
    T object;

}
