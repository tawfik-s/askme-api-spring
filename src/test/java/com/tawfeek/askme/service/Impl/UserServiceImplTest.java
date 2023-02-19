package com.tawfeek.askme.service.Impl;

import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.mapper.UserMapper;
import com.tawfeek.askme.model.Role;
import com.tawfeek.askme.model.auth.AuthenticationRequest;
import com.tawfeek.askme.model.user.UserRequestDTO;
import com.tawfeek.askme.model.user.UserResponseDTO;
import com.tawfeek.askme.repository.UserRepository;
import com.tawfeek.askme.security.JwtService;
import com.tawfeek.askme.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getUser() {
        var user=new User(1L,"tawfeek","123456", Role.USER,"t.shalash1@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(new UserResponseDTO(1L,"tawfeek","t.shalash1@gmail.com"));
        var res = userService.getUser(1L);
        assertThat(res).isNotNull();
        verify(userRepository,times(1)).findById(1L);
        assertThat(res.getEmail()).isEqualTo("t.shalash1@gmail.com");

    }

    @Test
    void findUsersByName() {
        var user1=new User(1L,"tawfeek","123456", Role.USER,"t.shalash1@gmail.com");
        var user2=new User(2L,"tawfeek","123456", Role.USER,"t.shalash1@gmail.com");
        List<User> arr1=new ArrayList<>();
        arr1.add(user1);
        arr1.add(user2);
        when(userRepository.findByUserName("tawfeek")).thenReturn(Optional.of(arr1));
        when(userMapper.toDTO(user1)).thenReturn(new UserResponseDTO(1L,"tawfeek","t.shalash1@gmail.com"));
        when(userMapper.toDTO(user2)).thenReturn(new UserResponseDTO(2L,"tawfeek","t.shalash1@gmail.com"));
        var res = userService.findUsersByName("tawfeek");
        assertThat(res).isNotNull();
        verify(userRepository,times(1)).findByUserName("tawfeek");
        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    void findUserByEmail() {
        var user=new User(1L,"tawfeek","123456", Role.USER,"t.shalash1@gmail.com");
        when(userRepository.findByEmail("t.shalash1@gmail.com")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(new UserResponseDTO(1L,"tawfeek","t.shalash1@gmail.com"));
        var res = userService.findUserByEmail("t.shalash1@gmail.com");
        assertThat(res).isNotNull();
        verify(userRepository,times(1)).findByEmail("t.shalash1@gmail.com");
        assertThat(res.getEmail()).isEqualTo("t.shalash1@gmail.com");
    }
}