package com.example.qatar2022.repository.personne;

import com.example.qatar2022.entities.personne.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoueurRepository extends JpaRepository<Joueur,Long> {

List<Joueur> findAllByEquipe_IdEquipe(Long idEquipe);






}