package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findById(long id);

    List<Image> findByNom(String nom);

    List<Image> findByType (String type);



}
