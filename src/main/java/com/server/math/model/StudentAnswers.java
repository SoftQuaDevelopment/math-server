package com.server.math.model;

import com.server.math.model.questions.Answer;
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

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "task_id")
    private Task task;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    private int points;

}
