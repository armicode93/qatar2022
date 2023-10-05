package com.example.qatar2022.service;

import com.example.qatar2022.dto.UserRegistrationDto;
import com.example.qatar2022.entities.personne.Role;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.repository.personne.RoleRepository;
import com.example.qatar2022.repository.personne.UserRepository;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private BCryptPasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository) {
    super();

    this.userRepository = userRepository;
  }

  @Override
  public User save(UserRegistrationDto registrationDto) {
    final User user = new User();

    user.setNom(registrationDto.getNom());
    user.setPrenom(registrationDto.getPrenom());
    user.setDateNaiss(registrationDto.getDateNaiss());
    user.setEmail(registrationDto.getEmail());
    user.setGsm(registrationDto.getGsm());
    user.setUsername(registrationDto.getUsername());
    user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

    Role userRole = roleRepository.findByName("ROLE_USER");
    if (userRole == null) {

      userRole = new Role();
      userRole.setName("ROLE_USER");
      roleRepository.save(userRole);
    }

    user.setRole(userRole);

    return userRepository.save(user);
  }

  @Override
  public void updateResetPasswordToken(String token, String email)
      throws UserPrincipalNotFoundException {

    User user = userRepository.findByEmail(email);

    if (user != null) {
      user.setResetPasswordToken(token);
      userRepository.save(user);
    } else {
      throw new UserPrincipalNotFoundException(
          "Could not find any customer with the email" + email);
    }
  }

  @Override
  public User getByResetPasswordToken(String token) {
    return userRepository.findByResetPasswordToken(token);
  }

  @Override
  public void updatePassword(User user, String newPassword) {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedPassword);

    user.setResetPasswordToken(null); // add null to the token
    userRepository.save(user);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public boolean isUsernameUnique(String username) {
    User user = userRepository.findByUsername(username);
    return user == null;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
  }

  // Methode to maps roles to authorities, we are going to convert Roles t oauthorities
  // becouse spring security expecting authorities
  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role.getName()));
    return authorities;
  }
}
