package com.tawfeek.askme.mapper;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.model.answer.AnswerRequestDTO;
import com.tawfeek.askme.model.answer.AnswerResponseDTO;
import com.tawfeek.askme.model.question.QuestionRequestDTO;

public interface AnswerMapper {

    public Answer toEntity(AnswerRequestDTO answerRequestDTO, Question question, User recipient);

    public AnswerResponseDTO toDTO(Answer answer);
}
