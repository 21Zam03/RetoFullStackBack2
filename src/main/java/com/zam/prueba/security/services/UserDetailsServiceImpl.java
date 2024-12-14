package com.zam.prueba.security.services;

import com.zam.prueba.entity.UserEntity;
import com.zam.prueba.repository.UserRepository;
import com.zam.prueba.security.customs.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> {
            return new UsernameNotFoundException("User not found");
        });
        return new CustomUserDetails(user);
    }

}
