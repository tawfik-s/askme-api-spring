package com.tawfeek.askme.service.Impl;

import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.exception.RecordNotFoundException;
import com.tawfeek.askme.mapper.UserMapper;
import com.tawfeek.askme.model.user.UserRequestDTO;
import com.tawfeek.askme.model.user.UserResponseDTO;
import com.tawfeek.askme.repository.UserRepository;
import com.tawfeek.askme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDTO getUser(Long id) {
       User user= userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> findUsersByName(String name) {
        List<User> users = userRepository.findByUserName(name).orElseThrow(RecordNotFoundException::new);
        return users.stream().map(user->userMapper.toDTO(user)).toList();
    }

    @Override
    public UserResponseDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(RecordNotFoundException::new);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream().map(user -> userMapper.toDTO(user)).toList();
    }
}
