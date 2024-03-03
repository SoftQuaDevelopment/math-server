package com.server.math.model;

import com.server.math.dto.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer classNumber;

    private Integer day;

    @Enumerated(EnumType.STRING)
    private Subject subject;


    @Column(length = 7000)
    private String text;

}
