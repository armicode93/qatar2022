package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.person.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findById(long idUser);
    User findByUsername(String username);


    Optional<User> getRole();
}
