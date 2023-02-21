package com.tawfeek.askme.model.questionAndAnswer;

import com.tawfeek.askme.model.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAndAnswerDTO {
    private Long questionId;

    private Long answerId;

    private String questionText;

    private UserResponseDTO sender; //may be null no problem

    private String answerText;

    private LocalDateTime answerTime;
}
