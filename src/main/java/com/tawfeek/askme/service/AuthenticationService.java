package com.tawfeek.askme.service;

import com.tawfeek.askme.model.auth.AuthenticationRequest;
import com.tawfeek.askme.model.auth.AuthenticationResponse;
import com.tawfeek.askme.model.user.UserRequestDTO;

public interface AuthenticationService {

    public AuthenticationResponse register(UserRequestDTO request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);


}
