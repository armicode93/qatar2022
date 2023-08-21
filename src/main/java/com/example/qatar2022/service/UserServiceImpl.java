package com.example.qatar2022.service;

import com.example.qatar2022.dto.UserRegistrationDto;
import com.example.qatar2022.entities.personne.Role;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.repository.personne.RoleRepository;
import com.example.qatar2022.repository.personne.UserRepository;
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

  @Autowired private UserRepository userRepository; // base injection
  @Autowired private RoleRepository roleRepository;
  @Autowired private BCryptPasswordEncoder passwordEncoder;


  public UserServiceImpl(UserRepository userRepository) {
    super();

    this.userRepository = userRepository;
  }

  @Override
  public User save(UserRegistrationDto registrationDto) {
    final User user = new User();

    // User user = new
    // User(registrationDto.getUsername(),passwordEncoder.encode(registrationDto.getPassword()),registrationDto.getNom(),registrationDto.getPrenom(),registrationDto.getEmail(),registrationDto.getDateNaiss(),
    //   registrationDto.getGsm(),Arrays.asList(roleRepository.findByName("ROLE_USER")));

    user.setUsername(registrationDto.getUsername());
    user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
    user.setNom(registrationDto.getNom());
    user.setPrenom(registrationDto.getPrenom());
    user.setEmail(registrationDto.getEmail());
    user.setDateNaiss(registrationDto.getDateNaiss());
    user.setGsm(registrationDto.getGsm());

    // Verifica se esiste il ruolo "ROLE_USER" nel database
    Role userRole = roleRepository.findByName("ROLE_USER");
    if (userRole == null) {
      // Se il ruolo non esiste, crea un nuovo ruolo "ROLE_USER"
      userRole = new Role();
      userRole.setName("ROLE_USER");
      roleRepository.save(userRole);
    }
    // Associa il ruolo "ROLE_USER" all'utente
    user.setRole(userRole);

    return userRepository.save(user);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user =
        userRepository.findByUsername(
            username); // prendiamo lo user object from the database //from the login screen we pass
                       // email adresse and password
    if (user == null) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
  }
  // Methode to maps roles to authorities, we are going to convert Roles t oauthorities
  // becouse spring security expecting authorities
  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
    // here im going to map  Roles to authorities

    //  return roles.stream().map(role -> new
    // SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    // we converted roles to stream,w maped a roles and we converted role into s.grantedAuthority,
    // is the spring security provided class
    // and after that we converted stream to a list
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role.getName()));
    return authorities;
  }


}
