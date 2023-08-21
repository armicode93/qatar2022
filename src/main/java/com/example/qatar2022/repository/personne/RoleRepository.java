package com.example.qatar2022.repository.personne;

import com.example.qatar2022.entities.personne.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

  Role findByName(String name);

  Role findById(long id);


}
