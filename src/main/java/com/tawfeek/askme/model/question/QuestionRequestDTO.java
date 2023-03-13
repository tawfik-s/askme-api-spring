package com.tawfeek.askme.model.question;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionRequestDTO {
    @NotNull
    private Long recipientId;

    @NotNull
    @Size(min=3,max=32,message="message body should be between 3 and 32")
    private String questionText;

    @NotNull
    private boolean anonymity;
}
