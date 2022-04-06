package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Spectateur;

import com.example.qatar2022.entities.person.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpectateurRepository extends JpaRepository<Spectateur,Long> {

    Spectateur findByCodeTicket(long codeTicket);

    List<Spectateur> findBySpectateur(Personne Personne);

    List<Spectateur> findByPartie(Partie partie);


}
