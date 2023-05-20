package com.tawfeek.askme.service;

import com.tawfeek.askme.model.user.UserRequestDTO;
import com.tawfeek.askme.model.user.UserResponseDTO;

import java.util.List;

public interface UserService {

    public UserResponseDTO getUser(Long id);

    public List<UserResponseDTO> findUsersByName(String name);

    public UserResponseDTO findUserByEmail(String email);

    public List<UserResponseDTO> getAllUsers();
}
