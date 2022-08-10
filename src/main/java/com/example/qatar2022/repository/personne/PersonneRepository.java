package com.example.qatar2022.repository.personne;

import com.example.qatar2022.entities.personne.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneRepository extends JpaRepository<Personne,Long> {

}

