package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {


}