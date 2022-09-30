/*package com.example.qatar2022.config;

import com.example.qatar2022.dto.UserRegistrationDto;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.repository.personne.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }

    @Override
    public User save(UserPrincipal userprincipal)
    {
        User user = new User(user.getUsername(), passwordEncoder().encode(user.getPassword())),
                registrationDto.getFirstname(),registrationDto.getLastname(),
                registrationDto.getEmail(),registrationDto.getLangue(),user.);
        //create a user object here
        return userRepository.save(user);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

 */
