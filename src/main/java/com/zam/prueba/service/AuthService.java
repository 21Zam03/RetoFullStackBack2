package com.zam.prueba.service;

import com.zam.prueba.payload.request.SignInRequest;
import com.zam.prueba.payload.request.SignUpRequest;
import com.zam.prueba.payload.response.SignInResponse;
import com.zam.prueba.payload.response.SignUpResponse;
import com.zam.prueba.payload.response.UserLoggedResponse;

public interface AuthService {

    public SignUpResponse signUp(SignUpRequest signUpRequest);
    public SignInResponse signIn(SignInRequest signInRequest);
    public UserLoggedResponse validateSession(String token);

}
