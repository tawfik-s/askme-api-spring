package com.tawfeek.askme.model.question;

import com.tawfeek.askme.model.user.UserResponseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {

    private Long id;
    private String questionText;

    private UserResponseDTO sender;
}
