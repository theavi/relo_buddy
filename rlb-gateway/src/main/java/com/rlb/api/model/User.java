package com.rlb.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // match DB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Integer id;

    private String name;

    @Column(name = "phone_no")
    private String phoneNo;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),       // FK in user_roles
            inverseJoinColumns = @JoinColumn(name = "role_id") // FK in user_roles
    )
    private Set<Role> roles = new HashSet<>();
}
