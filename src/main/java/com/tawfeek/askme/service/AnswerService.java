package com.tawfeek.askme.service;

import com.tawfeek.askme.model.answer.AnswerRequestDTO;
import com.tawfeek.askme.model.answer.AnswerResponseDTO;
import com.tawfeek.askme.model.questionAndAnswer.QuestionAndAnswerDTO;

import java.util.List;

public interface AnswerService {

    List<QuestionAndAnswerDTO> getUserAnsweredQuestions(Long userId);

    List<QuestionAndAnswerDTO> getMyAnsweredQuestions();

    AnswerResponseDTO AnswerQuestion(AnswerRequestDTO answerRequest);

    AnswerResponseDTO DeleteQuestionAnswer(Long answerId);
}
