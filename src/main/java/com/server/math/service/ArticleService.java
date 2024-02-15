package com.server.math.service;

import com.server.math.dto.Subject;
import com.server.math.model.Article;
import com.server.math.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> getArticleBySubject(Subject subject) {
        return articleRepository.findBySubject(subject);
    }

    public List<Article> getArticleByDay(int day) {
        return articleRepository.findByDay(day);
    }

    public List<Article> getArticleByClass(int classNumber) {
        return articleRepository.findByClassNumber(classNumber);
    }

    public List<Article> getArticleBySubjectAndDay(Subject subject, int day) {
        return articleRepository.findByDayAndSubject(day, subject);
    }

    public List<Article> getArticleBySubjectAndDayAndClass(Subject subject, int day, int classNumber) {
        return articleRepository.findByClassNumberAndDayAndSubject(classNumber, day, subject);
    }

    public List<Article> getArticleBySubjectAndClass(Subject subject, int classNumber) {
        return articleRepository.findBySubjectAndClassNumber(subject, classNumber);
    }


}
