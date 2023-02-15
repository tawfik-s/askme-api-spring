package com.tawfeek.askme.mapper.Impl;


import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.mapper.UserMapper;
import com.tawfeek.askme.model.user.UserRequestDTO;
import com.tawfeek.askme.model.user.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public  User toEntity(UserRequestDTO userRequestDTO) {
        User newUser = new User();
        newUser.setUserName(userRequestDTO.getUserName());
        newUser.setPassword(userRequestDTO.getPassword());
        newUser.setEmail(userRequestDTO.getEmail());
        return newUser;
    }

    @Override
    public  UserResponseDTO toDTO(User user) {
        UserResponseDTO newUserResponseDTO = new UserResponseDTO();
        newUserResponseDTO.setId(user.getId());
        newUserResponseDTO.setUserName(user.getUserName());
        newUserResponseDTO.setEmail(user.getEmail());
        return newUserResponseDTO;
    }
}
