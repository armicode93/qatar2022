package com.example.qatar2022.service.personne;


import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.repository.personne.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser()
    {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);
        return users;
    }
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
    public User getUserById(Long idUser)
    {
        return userRepository.findById(idUser).orElse(null);
    }

    public void addUser(User user)
    {
        userRepository.save(user);
    }
    public void deleteUser(Long idUser)
    {
        boolean exists = userRepository.existsById(idUser);

        if(!exists)
        {
            throw new IllegalStateException("Not exists");
        }
        userRepository.deleteById(idUser);
    }

    public User updateUser(Long idUser, User user)
    {
        userRepository.save(user);
        return user;
    }
}
