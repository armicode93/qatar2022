package com.example.qatar2022.service.person;


import com.example.qatar2022.entities.person.User;
import com.example.qatar2022.repository.person.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser()
    {
        List<User>user= new ArrayList<>();

        userRepository.findAll().forEach(user::add);
        return user;
    }
    public void addUser(User user)
    {
        userRepository.save(user);
    }
    public void updateUser(Long cin, User user)
    {
        userRepository.save(user);
    }
    public void deleteUser(Long cin)
    {
        userRepository.deleteById(cin);
    }
}
