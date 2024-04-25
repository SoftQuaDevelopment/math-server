package com.server.math.dto;

import com.server.math.model.Article;
import com.server.math.model.questions.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class RandomTask {

    Article article;
    List<Task> quiz;
    List<Task> task;


}
