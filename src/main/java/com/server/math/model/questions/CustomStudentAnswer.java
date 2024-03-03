package com.server.math.model.questions;

import com.server.math.model.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "custom_student_answers")
@Getter
@Setter
@NoArgsConstructor
public class CustomStudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(length = 1000)
    private String studentAnswerText;

    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
