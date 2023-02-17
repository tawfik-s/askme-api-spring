package com.tawfeek.askme.service.Impl;


import com.tawfeek.askme.mapper.UserMapper;
import com.tawfeek.askme.model.auth.AuthenticationRequest;
import com.tawfeek.askme.model.auth.AuthenticationResponse;
import com.tawfeek.askme.model.user.UserRequestDTO;
import com.tawfeek.askme.repository.UserRepository;
import com.tawfeek.askme.security.JwtService;
import com.tawfeek.askme.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private  JwtService jwtService;

  private final AuthenticationManager authenticationManager;



  public AuthenticationResponse register(UserRequestDTO request) {
    var user = userMapper.toEntity(request);
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}
