package com.example.qatar2022.repository.personne;


import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.personne.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    boolean existsByEmail(String email);

    User findById(long idUser);
    //User findUserByIdUser(long idUser);

    User findByEmail(String email);
}
