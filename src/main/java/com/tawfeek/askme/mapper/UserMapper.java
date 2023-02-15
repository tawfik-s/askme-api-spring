package com.tawfeek.askme.mapper;

import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.model.user.UserRequestDTO;
import com.tawfeek.askme.model.user.UserResponseDTO;

public interface UserMapper {

    public User toEntity(UserRequestDTO userRequestDTO);

    public UserResponseDTO toDTO(User user);
}
