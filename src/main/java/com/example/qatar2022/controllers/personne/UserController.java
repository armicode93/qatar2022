package com.example.qatar2022.controllers.personne;


import com.example.qatar2022.entities.personne.Personne;
import com.example.qatar2022.entities.personne.Role;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.service.personne.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.cert.Extension;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/User")
@CrossOrigin(origins = "*")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")

    public ResponseEntity findAll()
    {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/user")
    public ResponseEntity WhoImI (HttpServletRequest request)
    {
        return ResponseEntity.ok(userService.findByUsername(request.getRemoteUser()));
    }

    @GetMapping("/{idUser}")
    public ResponseEntity findUserById(@PathVariable(name="idUser") Long idUser)
    {
        if(idUser == null)
        {
            return ResponseEntity.badRequest().body("Empty parametre");
        }

        Optional <User> user = Optional.ofNullable(userService.getUserById(idUser));

        if(user.isPresent())
        {
            return ResponseEntity.ok(user);
        }

        else
        {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/Inscription")
    public ResponseEntity createUser(@RequestBody User userBody) {
        if (userBody == null) {
            return ResponseEntity.badRequest().body("Empty request Body");
        }

        Optional<User> user = Optional.ofNullable(userService.getUserById(userBody.getCin()));
        Optional<User> user2 = Optional.ofNullable(userService.findByUsername(userBody.getUsername()));

        if (!user.isPresent() && !user2.isPresent()) {
            user = Optional.ofNullable(userService.findByUsername(userBody.getUsername()));

            if (!user.isPresent()) {
                if (userBody.getPassword() != null) {
                    userBody.setPassword((userBody.getPassword()));
                }
                List<Role> role = userBody.getRole();
                role.add(Role.ROLE_CLIENT);
                userBody.setRole(role);
                userService.addUser(userBody);
                return ResponseEntity.ok(userBody);
            }


        }
        return ResponseEntity.badRequest().body("CIN/Username exist");
    }


        @CrossOrigin(origins = "*")
        @PostMapping("/addAdmin")
        public ResponseEntity createUserAdmin(@RequestBody User userBody) {
            if (userBody == null) {
                return ResponseEntity.badRequest().body("Empty request Body");
            }


            Optional<User> user = Optional.ofNullable(userService.getUserById(userBody.getCin()));

            if (!user.isPresent()) {
                user = Optional.ofNullable(userService.findByUsername(userBody.getUsername()));

                if (!user.isPresent()) {
                    if (userBody.getPassword() != null) {
                        userBody.setPassword(userBody.getPassword());
                    }

                    List<Role> role = userBody.getRole();
                    role.add(Role.ROLE_ADMIN);
                    userBody.setRole(role);
                    userService.addUser(userBody);

                    return ResponseEntity.ok(userBody);
                }
                }
                return ResponseEntity.badRequest().body("CIN/Username exist");

            }


        @DeleteMapping("/{userId}")

        public ResponseEntity deleteUser (@PathVariable(name = "userId") Long userId)
            {
                if(userId == null)
                {
                    return ResponseEntity.badRequest().body("Empty parameter");
                }

                Optional <User>  user = Optional.ofNullable(userService.getUserById(userId));

                if(user.isPresent())
                {
                    userService.deleteUser(userId);
                    return ResponseEntity.ok().body(user);
                }
                return ResponseEntity.notFound().build();

            }

            @PutMapping("/")
        public ResponseEntity updateUser(@RequestBody User userBody)
            {
                if(userBody== null)
                {
                    return ResponseEntity.badRequest().body("Empty Request Bddy");
                }

                Optional <User> user = Optional.ofNullable(userService.getUserById(userBody.getCin()));

                if(user.isPresent())
                {
                    List<Role> role = userBody.getRole();

                    userBody.setRole(role);
                    User createUser = userService.updateUser(userBody.getCin(),userBody);

                    return ResponseEntity.ok(createUser);
                }
                return ResponseEntity.notFound().build();


            }
     /*   @Bean
    private PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


      */
}
