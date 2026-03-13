package auca.ac.rw.CustomerComplaintSystem.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.CustomerComplaintSystem.domain.ELocationType;
import auca.ac.rw.CustomerComplaintSystem.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    // existsBy checks if a record with this code exists — returns true or false
    boolean existsByCode(String code);

    // Used to find a province when searching by code
    Optional<Location> findByCode(String code);

    // Used to find a province when searching by name
    Optional<Location> findByName(String name);

    // Used in recursive traversal — gets all direct children of a location
    List<Location> findByParent(Location parent);

    // Optional: find all locations of a given type (e.g., all PROVINCE)
    List<Location> findByType(ELocationType type);
}