package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.person.Joueur;
import com.example.qatar2022.entities.person.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonneRepository extends JpaRepository<Personne,Long> {


   List <Joueur> findAllPersonne(long cin);

   List<Joueur> findByNom(String nom);

   List<Joueur> findByPrenom(String prenom);






}
