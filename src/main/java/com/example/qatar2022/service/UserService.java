package com.example.qatar2022.service;

import com.example.qatar2022.dto.UserRegistrationDto;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.personne.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);



}


