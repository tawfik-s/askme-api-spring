package com.tawfeek.askme.controller;

import com.tawfeek.askme.model.answer.AnswerRequestDTO;
import com.tawfeek.askme.model.answer.AnswerResponseDTO;
import com.tawfeek.askme.model.questionAndAnswer.QuestionAndAnswerDTO;
import com.tawfeek.askme.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuestionAndAnswerDTO>> getUserAnsweredQuestions(@PathVariable Long userId) {
        List<QuestionAndAnswerDTO> userAnsweredQuestions = answerService.getUserAnsweredQuestions(userId);
        return ResponseEntity.ok(userAnsweredQuestions);
    }

    @GetMapping("/my")
    public ResponseEntity<List<QuestionAndAnswerDTO>> getMyAnsweredQuestions() {
        List<QuestionAndAnswerDTO> myAnsweredQuestions = answerService.getMyAnsweredQuestions();
        return ResponseEntity.ok(myAnsweredQuestions);
    }

    @PostMapping
    public ResponseEntity<AnswerResponseDTO> answerQuestion(@Valid @RequestBody AnswerRequestDTO answerRequest) {
        AnswerResponseDTO answerResponse = answerService.AnswerQuestion(answerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(answerResponse);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDTO> deleteQuestionAnswer(@PathVariable Long answerId) {
        AnswerResponseDTO deletedAnswer = answerService.DeleteQuestionAnswer(answerId);
        return ResponseEntity.ok(deletedAnswer);
    }
}

