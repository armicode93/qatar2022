package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.person.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

}
