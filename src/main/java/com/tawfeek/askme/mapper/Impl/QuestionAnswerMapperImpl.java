package com.tawfeek.askme.mapper.Impl;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.mapper.QuestionAnswerMapper;
import com.tawfeek.askme.mapper.UserMapper;
import com.tawfeek.askme.model.questionAndAnswer.QuestionAndAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionAnswerMapperImpl implements QuestionAnswerMapper {
    @Autowired private UserMapper userMapper;
    @Override
    public QuestionAndAnswerDTO toDTO(Answer questionAnswer) {
        QuestionAndAnswerDTO questionAndAnswerDTO=new QuestionAndAnswerDTO();
        questionAndAnswerDTO.setAnswerId(questionAnswer.getId());
        questionAndAnswerDTO.setQuestionText(questionAnswer.getQuestion().getQuestionText());
        questionAndAnswerDTO.setAnswerText(questionAnswer.getAnswerText());
        if(Boolean.FALSE.equals(questionAnswer.getQuestion().getAnonymity()))
             questionAndAnswerDTO.setSender(userMapper.toDTO(questionAnswer.getQuestion().getSender()));
        questionAndAnswerDTO.setQuestionId(questionAnswer.getQuestion().getId());
        return questionAndAnswerDTO;
    }
}
