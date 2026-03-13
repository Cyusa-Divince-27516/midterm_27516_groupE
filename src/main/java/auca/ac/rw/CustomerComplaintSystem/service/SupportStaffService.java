package auca.ac.rw.CustomerComplaintSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.CustomerComplaintSystem.domain.SupportStaff;
import auca.ac.rw.CustomerComplaintSystem.repository.SupportStaffRepository;

@Service
public class SupportStaffService {

    @Autowired
    private SupportStaffRepository staffRepo;

    
    //  Saves a support staff member.
    //  Uses existsByEmail() to prevent duplicates.
     
    public String saveStaff(SupportStaff staff) {
        if (staffRepo.existsByEmail(staff.getEmail())) {
            return "Staff with this email already exists.";
        }
        staffRepo.save(staff);
        return "Support staff saved successfully.";
    }

    public List<SupportStaff> getAllStaff() {
        return staffRepo.findAll();
    }
}