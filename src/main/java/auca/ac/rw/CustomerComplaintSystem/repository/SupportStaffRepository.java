package auca.ac.rw.CustomerComplaintSystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.CustomerComplaintSystem.domain.SupportStaff;

@Repository
public interface SupportStaffRepository extends JpaRepository<SupportStaff, UUID> {

    // Prevent duplicate staff emails
    boolean existsByEmail(String email);
}