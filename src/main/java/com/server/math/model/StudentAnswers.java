package com.server.math.model;

import com.server.math.model.questions.Answer;
import com.server.math.model.questions.CustomStudentAnswer;
import com.server.math.model.questions.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "student_answers")
@Getter
@Setter
public class StudentAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "custom_student_answer_id")
    private CustomStudentAnswer customStudentAnswer;

    private int points;

}
