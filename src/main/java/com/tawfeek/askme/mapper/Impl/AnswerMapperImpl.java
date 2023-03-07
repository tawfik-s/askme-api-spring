package com.tawfeek.askme.mapper.Impl;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.mapper.AnswerMapper;
import com.tawfeek.askme.mapper.QuestionMapper;
import com.tawfeek.askme.mapper.UserMapper;
import com.tawfeek.askme.model.answer.AnswerRequestDTO;
import com.tawfeek.askme.model.answer.AnswerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AnswerMapperImpl implements AnswerMapper {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Answer toEntity(AnswerRequestDTO answerRequestDTO, Question question, User recipient) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAnswerOwner(recipient);
        answer.setAnswerText(answerRequestDTO.getAnswerText());
        answer.setCreatedAt(LocalDateTime.now());
        return answer;
    }

    @Override
    public AnswerResponseDTO toDTO(Answer answer) {
        return new AnswerResponseDTO(answer.getId(),
                        answer.getAnswerText(),
                        userMapper.toDTO(answer.getAnswerOwner()),
                        questionMapper.toDTO(answer.getQuestion()));
    }
}
