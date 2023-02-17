package com.tawfeek.askme.service.Impl;

import com.tawfeek.askme.model.user.UserRequestDTO;
import com.tawfeek.askme.model.user.UserResponseDTO;
import com.tawfeek.askme.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponseDTO getUser(Long id) {
        return null;
    }

    @Override
    public UserResponseDTO AddUser(UserRequestDTO userRequestDTO) {
        return null;
    }

    @Override
    public List<UserResponseDTO> findUsersByName(String name) {
        return null;
    }

    @Override
    public UserRequestDTO findUserByEmail(String email) {
        return null;
    }
}
