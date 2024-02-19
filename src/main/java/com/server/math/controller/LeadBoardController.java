package com.server.math.controller;

import com.server.math.dto.LeadBoard;
import com.server.math.dto.Subject;
import com.server.math.service.LeadBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LeadBoardController {


    @Autowired
    LeadBoardService leadBoardService;

    @GetMapping("/leadBoard.getBySubjectAndClassNumberAndClassLetter")
    private ResponseEntity<?> getLeadBordBySubjectAndClassNumberAndClassLetter(@RequestParam(name = "classNumber") int classNumber,
                                                                               @RequestParam(name = "classLetter") String classLetter,
                                                                               @RequestParam(name = "subject") Subject subject) {
        List<LeadBoard> leadBoards = leadBoardService.getLeadBordBySubjectAndClassNumberAndClassLetter(classNumber, classLetter, subject);
        return new ResponseEntity<>(leadBoards, HttpStatus.OK);
    }

    @GetMapping("/leadBoard.getByClassNumberAndClassLetter")
    private ResponseEntity<?> getLeadBoardByClassNumberAndClassLetter(@RequestParam(name = "classNumber") int classNumber,
                                                                      @RequestParam(name = "classLetter") String classLetter) {
        List<LeadBoard> leadBoards = leadBoardService.getLeadBoardByClassNumberAndClassLetter(classNumber, classLetter);
        return new ResponseEntity<>(leadBoards, HttpStatus.OK);
    }

    @GetMapping("/leadBoard.getBySubjectAndClassNumber")
    private ResponseEntity<?> getLeadBordBySubjectAndClassNumber(@RequestParam(name = "classNumber") int classNumber,
                                                                               @RequestParam(name = "subject") Subject subject) {
        List<LeadBoard> leadBoards = leadBoardService.getLeadBordBySubjectAndClassNumber(classNumber, subject);
        return new ResponseEntity<>(leadBoards, HttpStatus.OK);
    }

    @GetMapping("/leadBoard.getByClassNumber")
    private ResponseEntity<?> getLeadBoardByClassNumber(@RequestParam(name = "classNumber") int classNumber) {
        List<LeadBoard> leadBoards = leadBoardService.getLeadBoardByClassNumber(classNumber);
        return new ResponseEntity<>(leadBoards, HttpStatus.OK);
    }

}
