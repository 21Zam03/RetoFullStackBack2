package com.zam.prueba.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zam.prueba.entity.RoleEntity;
import com.zam.prueba.entity.UserEntity;
import com.zam.prueba.exception.DuplicateException;
import com.zam.prueba.exception.NotFoundException;
import com.zam.prueba.payload.request.SignInRequest;
import com.zam.prueba.payload.request.SignUpRequest;
import com.zam.prueba.payload.response.SignInResponse;
import com.zam.prueba.payload.response.SignUpResponse;
import com.zam.prueba.payload.response.UserLoggedResponse;
import com.zam.prueba.repository.RoleRepository;
import com.zam.prueba.repository.UserRepository;
import com.zam.prueba.service.AuthService;
import com.zam.prueba.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DuplicateException
                    ("Email "+ signUpRequest.getEmail() +
                            " is already registered in the system");
        }

        Set<RoleEntity> roleList = new HashSet<>();
        signUpRequest.getRoleList().forEach(roleId -> {
            RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() ->
                    new NotFoundException("Role with id "+roleId+" was not fond" ));
            roleList.add(roleEntity);
        });

        UserEntity userToCreate = UserEntity.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roleList(roleList)
                .build();

        UserEntity userCreated = userRepository.save(userToCreate);

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userCreated.getRoleList().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        userCreated.getRoleList()
                .stream()
                .flatMap(roleEntity -> roleEntity.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        String accessToken = jwtUtil.createToken(userCreated.getEmail(), authorityList);
        return SignUpResponse.builder()
                .email(userCreated.getEmail())
                .message("Client was registered successfully")
                .token(accessToken)
                .status(200)
                .build();
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        Authentication authentication = authenticateUser(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /*Si queremos obtener el usuario logeado deberiamos obtenerlo de esta forma */
        //CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtUtil.createToken(authentication.getName(), authentication.getAuthorities());
        return SignInResponse.builder()
                .email(email)
                .message("User logged successfully")
                .token(accessToken)
                .status(200)
                .build();
    }

    @Override
    public UserLoggedResponse validateSession(String token) {
        DecodedJWT decodedJWT = jwtUtil.verifyToken(token);
        String email = jwtUtil.extractUsername(decodedJWT);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        return UserLoggedResponse.builder()
                .profilePicture(user.getProfilePicture())
                .email(user.getEmail())
                .build();
    }

    public Authentication authenticateUser(String email, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }

}
