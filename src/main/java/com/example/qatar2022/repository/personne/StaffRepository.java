package com.example.qatar2022.repository.personne;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.personne.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    List<Staff> findByEquipe(Equipe equipe);
}

