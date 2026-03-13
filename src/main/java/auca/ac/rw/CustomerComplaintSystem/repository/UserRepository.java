package auca.ac.rw.CustomerComplaintSystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.CustomerComplaintSystem.domain.Location;
import auca.ac.rw.CustomerComplaintSystem.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // existsBy — checks if email is already registered
    boolean existsByEmail(String email);

    // find users whose location is in the province hierarchy
    List<User> findByLocationIn(List<Location> locations);

    

}