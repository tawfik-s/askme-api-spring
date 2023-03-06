package com.tawfeek.askme.model.answer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequestDTO {

    @NotNull
    private Long questionId;

    @NotNull
    @Size(min = 1,max = 1000,message = "answer should be less than 1000 ")
    private String answerText;
}
