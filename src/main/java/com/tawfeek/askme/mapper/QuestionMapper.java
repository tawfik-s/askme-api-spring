package com.tawfeek.askme.mapper;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.model.question.QuestionRequestDTO;
import com.tawfeek.askme.model.question.QuestionResponseDTO;

public interface QuestionMapper {
    public Question toEntity(QuestionRequestDTO questionRequestDTO, User recipient, User sender);
    public QuestionResponseDTO toDTO(Question question);
}
