package com.tawfeek.askme.model.answer;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.model.question.QuestionRequestDTO;
import com.tawfeek.askme.model.question.QuestionResponseDTO;
import com.tawfeek.askme.model.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseDTO {
    private Long answerId;

    private String answerText;

    private UserResponseDTO recipient;

    private QuestionResponseDTO question;
}
