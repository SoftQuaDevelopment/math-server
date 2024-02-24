package com.server.math.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(columnNames = "telegramId")
})
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String lastName;
    private Long telegramId;
    private Integer classNumber;
    private String classLetter;
    private boolean isBan;
}
