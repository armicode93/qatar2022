package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.person.Joueur;
import com.example.qatar2022.entities.person.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {

    List<Staff> findByEquipe(Equipe equipe);

    List<Staff> findByFonction (String fonction);
}
