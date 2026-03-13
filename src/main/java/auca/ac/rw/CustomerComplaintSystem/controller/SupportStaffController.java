package auca.ac.rw.CustomerComplaintSystem.controller;

import auca.ac.rw.CustomerComplaintSystem.domain.SupportStaff;
import auca.ac.rw.CustomerComplaintSystem.service.SupportStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class SupportStaffController {

    @Autowired
    private SupportStaffService staffService;

    // POST /api/staff/save
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveStaff(@RequestBody SupportStaff staff) {
        String response = staffService.saveStaff(staff);

        if (response.equals("Support staff saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    // GET /api/staff/all
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllStaff() {
        List<SupportStaff> staffList = staffService.getAllStaff();
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }
}