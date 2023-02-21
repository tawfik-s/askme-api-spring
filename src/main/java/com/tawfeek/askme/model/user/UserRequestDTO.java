package com.tawfeek.askme.model.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDTO {

    @NotNull
    @Size(min=3,max=32,
            message = "name size should be between" +
                    " 3 and 32 digit or character")
    private String userName;

    @NotNull(message = "can't be null to create new user")
    @Size(min=5,max=32,
            message = "password size should be between" +
                    " 5 and 32 digit or character")
    private String password;


    @NotNull
    @Size(min=5,max=32,
            message = "password size should be between" +
                    " 5 and 32 digit or character")
    private String email;
}
