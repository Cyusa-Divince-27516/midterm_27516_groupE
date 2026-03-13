package auca.ac.rw.CustomerComplaintSystem.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import auca.ac.rw.CustomerComplaintSystem.domain.Location;
import auca.ac.rw.CustomerComplaintSystem.domain.User;
import auca.ac.rw.CustomerComplaintSystem.repository.LocationRepository;
import auca.ac.rw.CustomerComplaintSystem.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private LocationService locationService;

   
//     Saves a user.
//    - Checks for duplicate email using existsByEmail().
//    - Attaches location if locationId is provided.
   
    public String saveUser(User user, String locationId) {

        // existsBy check — prevents duplicate emails
        if (userRepo.existsByEmail(user.getEmail())) {
            return "User with this email already exists.";
        }

        // Attach location to user
        if (locationId != null) {
            Location location = locationRepo.findById(UUID.fromString(locationId)).orElse(null);
            if (location == null) {
                return "Location not found.";
            }
            user.setLocation(location);
        }

        userRepo.save(user);
        return "User saved successfully.";
    }

    
    // Returns all users sorted by name A-Z.
    // Uses Spring Data JPA Sort — no SQL needed.
    
    public List<User> getAllUsersSorted() {
        return userRepo.findAll(Sort.by("name").ascending());
    }


    // Returns users in a paginated way.
    // page = which page (starts at 0), size = how many per page.
    // Pageable splits the table into pages — improves performance.
    
    public Page<User> getAllUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepo.findAll(pageable);
    }

    /**
     * Find all users in a given province (by code OR name).
     * - Gets all location IDs under the province (recursively).
     * - Then finds users attached to any of those locations.
     */
    
    public List<User> getUsersByProvince(String code, String name) {
        List<Location> locations = locationService.getAllLocationsUnderProvince(code, name);

        if (locations == null || locations.isEmpty()) {
            return null;
        }

        return userRepo.findByLocationIn(locations);
    }
}