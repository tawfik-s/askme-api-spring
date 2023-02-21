package com.tawfeek.askme.mapper;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.model.questionAndAnswer.QuestionAndAnswerDTO;

public interface QuestionAnswerMapper {
    public QuestionAndAnswerDTO toDTO(Answer questionAnswer);
}
