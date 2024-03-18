package com.server.math.controller;

import com.server.math.dto.ResultTable;
import com.server.math.dto.Subject;
import com.server.math.service.ResultTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResultTableController {

    @Autowired
    ResultTableService resultTableService;


    @GetMapping("/result.getBySubject")
    public ResponseEntity<?> getResultBySubject(@RequestParam(name = "subject") Subject subject) {
        List<ResultTable> resultTable = resultTableService.getResultBySubject(subject);
        return new ResponseEntity<>(resultTable, HttpStatus.OK);
    }

}
