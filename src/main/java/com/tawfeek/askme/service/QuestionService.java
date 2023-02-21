package com.tawfeek.askme.service;


import com.tawfeek.askme.model.question.QuestionRequestDTO;
import com.tawfeek.askme.model.question.QuestionResponseDTO;

import java.util.List;

public interface QuestionService {

    List<QuestionResponseDTO>getMyUnAnsweredQuestions();

    QuestionResponseDTO AskUserQuestion(QuestionRequestDTO questionRequest);


}
