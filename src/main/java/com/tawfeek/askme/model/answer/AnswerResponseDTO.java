package com.tawfeek.askme.model.answer;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponseDTO {
    private Long answerId;

    private String answerText;

    private User recipient;

    private Question question;
}
