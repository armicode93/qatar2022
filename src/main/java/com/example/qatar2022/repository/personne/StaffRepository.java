package com.example.qatar2022.repository.personne;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.personne.Staff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

  List<Staff> findAllByEquipe_IdEquipe(Long idEquipe);
}
