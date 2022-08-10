package com.example.qatar2022.service.personne;

import com.example.qatar2022.entities.personne.Staff;
import com.example.qatar2022.repository.personne.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {


    private final StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    public List<Staff> getAllStaff()
    {
        List<Staff> staff = new ArrayList<>();

        staffRepository.findAll().forEach(staff::add);

        return staff;
    }
    public Staff getStaffById(Long cin)
    {
        return  staffRepository.findById(cin).orElse(null);
    }

    public void addStaff(Staff staff)
    {
        staffRepository.save(staff);

    }
    public Staff updateStaff(Long cin, Staff staff)
    {
        staffRepository.save(staff);
        return staff;
    }
    public void deleteStaff(Long cin)
    {
        boolean exists = staffRepository.existsById(cin);
        if(!exists)
        {
            throw new IllegalStateException("Not exists");
        }
        staffRepository.deleteById(cin);
    }

}
