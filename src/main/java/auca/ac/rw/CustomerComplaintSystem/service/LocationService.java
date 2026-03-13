package auca.ac.rw.CustomerComplaintSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.CustomerComplaintSystem.domain.ELocationType;
import auca.ac.rw.CustomerComplaintSystem.domain.Location;
import auca.ac.rw.CustomerComplaintSystem.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepo;

    
    //  Saves a location.
    //   - If parentId is given, it links the location to its parent.
    //   - Non-PROVINCE locations MUST have a parent.
    //  - Duplicate codes are rejected.
   
    public String saveLocation(Location location, String parentId) {

        // Step 1: Check for duplicate code
        if (locationRepo.existsByCode(location.getCode())) {
            return "Location with this code already exists.";
        }

        // Step 2: If parentId is provided, find and attach the parent
        if (parentId != null) {
            Location parent = locationRepo.findById(UUID.fromString(parentId)).orElse(null);
            if (parent == null) {
                return "Parent location not found.";
            }
            location.setParent(parent);
        }

        // Step 3: Only PROVINCE can have no parent
        if (!location.getType().equals(ELocationType.PROVINCE) && parentId == null) {
            return "Parent location is required for non-province locations.";
        }

        // Step 4: Save
        locationRepo.save(location);
        return "Location saved successfully.";
    }

    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }

    
    //   Recursive method — adds a location and ALL its descendants into the list.
    //   Example: Province → all Districts → all Sectors → all Cells → all Villages
     
    private void collectChildren(Location parent, List<Location> list) {
        list.add(parent);
        List<Location> children = locationRepo.findByParent(parent);
        for (Location child : children) {
            collectChildren(child, list); // recursion
        }
    }

    
    //   Finds a province by code OR name, then collects all its descendant locations.
    //   Used by UserService to find users in a province.
     
    public List<Location> getAllLocationsUnderProvince(String code, String name) {
        Location province = null;

        if (code != null) {
            province = locationRepo.findByCode(code).orElse(null);
        } else if (name != null) {
            province = locationRepo.findByName(name).orElse(null);
        }

        if (province == null) {
            return null;
        }

        List<Location> allLocations = new ArrayList<>();
        collectChildren(province, allLocations);
        return allLocations;
    }
}