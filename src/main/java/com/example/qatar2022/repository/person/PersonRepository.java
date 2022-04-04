package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.person.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Personne,Long> {
}
