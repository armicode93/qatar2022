package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.person.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballerRepository extends JpaRepository<Joueur,Long> {
}
