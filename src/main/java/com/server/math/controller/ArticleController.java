package com.server.math.controller;

import com.server.math.dto.Subject;
import com.server.math.model.Article;
import com.server.math.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/article.create")
    private ResponseEntity<?> createArticle(@RequestBody Article article) {
        Article _article = articleService.createArticle(article);
        return new ResponseEntity<>(_article, HttpStatus.CREATED);
    }

    @GetMapping("/article.getBySubject")
    public ResponseEntity<?> getArticleBySubject(@RequestParam(name = "subject") Subject subject) {
        List<Article> articles = articleService.getArticleBySubject(subject);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/article.getByDay")
    public ResponseEntity<?> getArticleByDay(@RequestParam(name = "day") int day) {
        List<Article> articles = articleService.getArticleByDay(day);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/article.getByClass")
    public ResponseEntity<?> getArticleByClass(@RequestParam(name = "classNumber") int classNumber) {
        List<Article> articles = articleService.getArticleByClass(classNumber);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/article.getBySubjectAndDay")
    public ResponseEntity<?> getArticleBySubjectAndDay(@RequestParam(name = "subject") Subject subject,
                                                   @RequestParam(name = "day") int day) {
        List<Article> articles = articleService.getArticleBySubjectAndDay(subject, day);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/article.getBySubjectAndDayAndClass")
    public ResponseEntity<?> getArticleBySubjectAndDayAndClass(@RequestParam(name = "subject") Subject subject,
                                                               @RequestParam(name = "day") int day,
                                                               @RequestParam(name = "classNumber") int classNumber) {
        List<Article> articles = articleService.getArticleBySubjectAndDayAndClass(subject, day, classNumber);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/article.getBySubjectAndClass")
    public ResponseEntity<?> getArticleBySubjectAndClass(@RequestParam(name = "subject") Subject subject,
                                                     @RequestParam(name = "classNumber") int classNumber) {
        List<Article> articles = articleService.getArticleBySubjectAndClass(subject, classNumber);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

}
