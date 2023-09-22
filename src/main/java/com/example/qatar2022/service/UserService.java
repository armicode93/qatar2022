package com.example.qatar2022.service;

import com.example.qatar2022.dto.UserRegistrationDto;
import com.example.qatar2022.entities.personne.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
public interface UserService extends UserDetailsService {
  User save(UserRegistrationDto registrationDto);

  //sets the value for passwordToken of a user found by the given email

  void updateResetPasswordToken(String token, String email) throws UserPrincipalNotFoundException;

//find a User by the given resetPASSWORDTOKEN, random token is unique
  User getByResetPasswordToken(String token);


  //sets new password for the user using Bcrypt password encoding, and put null the resetPassword token
  void updatePassword(User user, String newPassword);

 boolean isUsernameUnique(String username);




}
