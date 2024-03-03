package com.server.math.dto;

import com.server.math.model.questions.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
public class AssignedStudentTask {

    Boolean isAssigned;

    Task task;

}
