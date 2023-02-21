package com.tawfeek.askme.mapper.Impl;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.mapper.QuestionMapper;
import com.tawfeek.askme.mapper.UserMapper;
import com.tawfeek.askme.model.question.QuestionRequestDTO;
import com.tawfeek.askme.model.question.QuestionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Autowired private UserMapper userMapper;
    @Override
    public Question toEntity(QuestionRequestDTO questionRequestDTO, User recipient,User sender) {
        Question question=new Question();
        question.setSender(sender);
        question.setRecipient(recipient);
        question.setQuestionText(questionRequestDTO.getQuestionText());
        question.setAnonymity(question.getAnonymity());
        question.setCreatedAt(LocalDateTime.now());
        return question;
    }

    @Override
    public QuestionResponseDTO toDTO(Question question) {
        var questionResponse = new QuestionResponseDTO();
        questionResponse.setQuestionText(question.getQuestionText());
        questionResponse.setId(question.getId());
        questionResponse.setSender(userMapper.toDTO(question.getSender()));
        return null;
    }
}
