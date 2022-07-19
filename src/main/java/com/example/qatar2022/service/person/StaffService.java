package com.example.qatar2022.service.person;

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


    public List<Staff> getAllStaff()
    {
        List<Staff> staff = new ArrayList<>();

        staffRepository.findAll().forEach(staff::add);

        return staff;
    }

    public void addStaff(Staff staff)
    {
        staffRepository.save(staff);

    }
    public void updateStaff(Long cin, Staff staff)
    {
    staffRepository.save(staff);
    }
    public void deleteArtist(Long cin)
    {
        staffRepository.deleteById(cin);
    }

}
