
package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe,Long> {

}
