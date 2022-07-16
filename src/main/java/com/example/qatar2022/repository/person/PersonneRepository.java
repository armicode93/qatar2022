package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.person.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonneRepository extends JpaRepository<Personne,Long> {

    Personne findByCin(long cin);

    /*List<Spectateur> findByNom(String nom);

    List<Spectateur> findByPrenom(String prenom);

     */


}
