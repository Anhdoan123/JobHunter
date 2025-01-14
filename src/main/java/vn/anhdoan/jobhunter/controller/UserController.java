package vn.anhdoan.jobhunter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.anhdoan.jobhunter.config.SecurityConfiguration;
import vn.anhdoan.jobhunter.domain.User;
import vn.anhdoan.jobhunter.service.UserService;
import vn.anhdoan.jobhunter.service.error.IdInvalidException;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User newUser) {
        String hashPass = this.passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashPass);
        User user = this.userService.handleCreatUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) throws IdInvalidException {
        if (id >= 30) {
            throw new IdInvalidException("asd");
        }
        User user = this.userService.getUserbyId(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public List<User> getAllUser() {
        List<User> users = this.userService.getAllUser();
        return users;
    }

    @PutMapping("/users")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        this.userService.handleUpdateUser(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
