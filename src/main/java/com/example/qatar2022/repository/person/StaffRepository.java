package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.person.Joueur;
import com.example.qatar2022.entities.person.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff,Long> {

    List<Staff> findByEquipe(Equipe equipe);

    List<Staff> findByFonction (String fonction);
}
