package com.example.qatar2022.controllers.Personne;


import com.example.qatar2022.entities.person.Personne;
import com.example.qatar2022.entities.person.Role;
import com.example.qatar2022.entities.person.User;
import com.example.qatar2022.repository.person.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/User")
@CrossOrigin(origins="*") //bloque requete de autre serveur, avec * all

public class UserController {


    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity findAll()
    {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/user")

    public ResponseEntity WhoImI(HttpServletRequest request){
        return ResponseEntity.ok(userRepository.findByUsername(request.getRemoteUser()));
    }


    @GetMapping("/user/{username}")
    public ResponseEntity findUserByUsername(@PathVariable(name ="username") String username) {
        if (username == null)
        {
            return ResponseEntity.badRequest().body("Empty parameter");

        }
        Optional <User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if(user.isPresent())
        {
            return ResponseEntity.ok(user);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody User userBody)
    {
        if(userBody == null)
        {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        //
        Optional<User> user = userRepository.findById(userBody.getCin());
        Optional<User> user2= Optional.ofNullable(userRepository.findByUsername(userBody.getUsername()));

        if (!user.isPresent() && !user2.isPresent()){
            user= Optional.ofNullable(userRepository.findByUsername(userBody.getUsername()));
            if(!user.isPresent()){
                if(userBody.getPassword()!=null){
                    userBody.setPassword(bCryptPasswordEncoder().encode(userBody.getPassword()));

                }

                List <Role> role = userBody.getRole();

                role.add(Role.ROLE_ADMIN);
                userBody.setRole(role);
                Personne createPersonne = userRepository.save(userBody);


                return ResponseEntity.ok(createPersonne);


            }
        }
        return ResponseEntity.badRequest().body("CIN/Username exist");
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable(name = "userId")Long userId)
    {
        if (userId == null)
        {
            ResponseEntity.badRequest().body("Empty parameter");
        }
        Optional <User> user = userRepository.findById(userId);

        if(user.isPresent())
        {
            userRepository.deleteById(userId);
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/")
    public ResponseEntity updateUser(@RequestBody User userBody)
    {
        if(userBody == null)
        {
            return ResponseEntity.badRequest().body("Empty Request Body");

        }

        Optional <User> user = userRepository.findById(userBody.getCin());

        if(user.isPresent())
        {
                List<Role> role = userBody.getRole();

                userBody.setRole(role);
                User createUser = userRepository.save(userBody);
                return ResponseEntity.ok(createUser);
        }
        return ResponseEntity.notFound().build();

    }


    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
