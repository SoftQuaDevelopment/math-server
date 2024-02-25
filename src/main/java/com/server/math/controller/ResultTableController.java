package com.server.math.controller;

import com.server.math.service.ResultTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultTableController {

    @Autowired
    ResultTableService resultTableService;

    @GetMapping("/resultTable.get")
    public ResponseEntity<?> generateTable(@RequestParam(name = "pageNumber") int pageNumber,
                                           @RequestParam(name = "pageSize") int pageSize) {
        return new ResponseEntity<>(resultTableService.generateTable(pageNumber, pageSize), HttpStatus.OK);
    }

}
