package com.booleanuk.simpleapi.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public User getOneUser(@PathVariable int id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User createUser(@RequestBody User user) {
        User newUser = new User(user.getFirstName(), user.getLastName());
        return this.userRepository.save(newUser);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id) {
        User userToUpdate = this.userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        );

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        return this.userRepository.save(userToUpdate);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable int id) {
        User userToDelete = this.userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        );
        this.userRepository.delete(userToDelete);
        return userToDelete;
    }
}