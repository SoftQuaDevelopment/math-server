package com.server.math.repository;

import com.server.math.dto.Subject;
import com.server.math.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findBySubject(Subject subject);

    List<Article> findByDay(Integer day);

    List<Article> findByDayAndSubject(Integer day, Subject subject);

    List<Article> findByClassNumber(Integer classNumber);

    List<Article> findByClassNumberAndDayAndSubject(Integer classNumber, Integer day, Subject subject);

    List<Article> findBySubjectAndClassNumber(Subject subject, Integer classNumber);
}