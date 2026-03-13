package auca.ac.rw.CustomerComplaintSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.CustomerComplaintSystem.domain.User;
import auca.ac.rw.CustomerComplaintSystem.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /api/users/save?locationId=xxx
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(
            @RequestBody User user,
            @RequestParam(required = false) String locationId) {

        String response = userService.saveUser(user, locationId);

        if (response.equals("User saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    // GET /api/users/allSorted — returns users sorted by name A-Z
    @GetMapping(value = "/allSorted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsersSorted() {
        List<User> users = userService.getAllUsersSorted();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // GET /api/users/all?page=0&size=5 — paginated result
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<User> users = userService.getAllUsersPaginated(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // GET /api/users/byProvince?code=KIG  OR  /byProvince?name=Kigali
    @GetMapping(value = "/byProvince", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByProvince(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name) {

        List<User> users = userService.getUsersByProvince(code, name);

        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No users found in the given province.", HttpStatus.NOT_FOUND);
        }
    }
}