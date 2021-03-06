package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Poste;
import com.example.qatar2022.entities.person.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoueurRepository extends JpaRepository<Joueur,Long> {



   List <Poste> findAllByPoste(Poste poste);

   List<Joueur> findAllByEquipe_IdEquipe(Long idEquipe);





}
