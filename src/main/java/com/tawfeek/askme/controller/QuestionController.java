package com.tawfeek.askme.controller;

import com.tawfeek.askme.model.question.QuestionRequestDTO;
import com.tawfeek.askme.model.question.QuestionResponseDTO;
import com.tawfeek.askme.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/unanswered")
    public ResponseEntity<List<QuestionResponseDTO>> getMyUnansweredQuestions() {
        List<QuestionResponseDTO> unansweredQuestions = questionService.getMyUnAnsweredQuestions();
        return ResponseEntity.ok(unansweredQuestions);
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> askUserQuestion(@RequestBody QuestionRequestDTO questionRequest) {
        QuestionResponseDTO questionResponse = questionService.AskUserQuestion(questionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);
    }
}
