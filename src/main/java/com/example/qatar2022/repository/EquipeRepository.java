package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Equipe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

  Optional<Equipe> findEquipeByIdEquipe(Long idEquipe);
}
