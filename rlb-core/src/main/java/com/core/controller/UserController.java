package com.core.controller;

import com.core.model.User;
import com.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        System.out.println("HTTP POST initiated for create User.");
        User created = userService.create(user);  // Using Hibernate DAO for create
        return ResponseEntity.ok(created);
    }

    // Delete a user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        System.out.println("HTTP DELETE initiated for delete User.");
        userService.delete(id);  // Using Hibernate DAO for delete
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        System.out.println("HTTP PUT initiated for update User.");
        User updated = userService.update(user);  // Using Hibernate DAO for update
        return ResponseEntity.ok(updated);
    }
}
