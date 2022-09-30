package com.example.qatar2022.repository.personne;


import com.example.qatar2022.entities.personne.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    User findById(long id);

    User findByEmail(String email);
}
