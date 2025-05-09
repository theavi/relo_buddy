package com.core.controller;

import com.core.entity.Role;
import com.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Role role) {
        System.out.println("HTTP POST initiated for create Role.");
        Role created = roleService.create(role);  // Using Hibernate DAO for create
        return ResponseEntity.ok(created);
    }

    // Delete a role by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        System.out.println("HTTP DELETE initiated for delete Role.");
        roleService.delete(id);  // Using Hibernate DAO for delete
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Role> update(@RequestBody Role role) {
        System.out.println("HTTP PUT initiated for update Role.");
        Role updated = roleService.update(role);  // Using Hibernate DAO for update
        return ResponseEntity.ok(updated);
    }
}
