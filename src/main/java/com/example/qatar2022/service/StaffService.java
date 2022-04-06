package com.example.qatar2022.service;

import com.example.qatar2022.entities.person.Staff;
import com.example.qatar2022.repository.person.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    //injection of dependences


    public List<Staff> getAllStaffs() {
        List<Staff> staffs = new ArrayList<>();

        staffRepository.findAll().forEach(staffs::add);

        return staffs;

    }

    public void addStaff(Staff staff)
    {
        staffRepository.save(staff);
    }



}
