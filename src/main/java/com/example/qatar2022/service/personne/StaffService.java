package com.example.qatar2022.service.personne;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.personne.Staff;
import com.example.qatar2022.repository.personne.StaffRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

  private final StaffRepository staffRepository;

  @Autowired
  public StaffService(StaffRepository staffRepository) {
    this.staffRepository = staffRepository;
  }

  public List<Staff> getAllStaff() {
    List<Staff> staff = new ArrayList<>();

    staffRepository.findAll().forEach(staff::add);

    return staff;
  }

  public Staff getStaffById(Long idStaff) {
    return staffRepository.findById(idStaff).orElse(null);
  }

  public List<Staff> getStaffByEquipe(Equipe equipe) {
    return staffRepository.findByEquipe(equipe);
  }

  public void addStaff(Staff staff) {
    staffRepository.save(staff);
  }

  public Staff updateStaff(Long idStaff, Staff staff) {
    staffRepository.save(staff);
    return staff;
  }

  public void deleteStaff(Long idStaff) {
    boolean exists = staffRepository.existsById(idStaff);
    if (!exists) {
      throw new IllegalStateException("Not exists");
    }
    staffRepository.deleteById(idStaff);
  }
}
