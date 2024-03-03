package com.server.math.model.questions;

import com.server.math.dto.Subject;
import com.server.math.dto.TaskType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer classNumber;

    private Integer day;

    private boolean isQuiz;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private String topic;

    private Integer difficultyLevel;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Column(length = 5000)
    private String text;


    @Column(length = 5000)
    private String content;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "task_answers",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "answers_id"))
    private Set<Answer> answers = new LinkedHashSet<>();

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.getTasks().add(this);
    }

}
