    package com.core.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.Data;

    import java.util.HashSet;
    import java.util.Set;

    @Data
    @Entity
    public class Role {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String name;

        @ManyToMany(mappedBy = "roles")
        @JsonIgnore
        private Set<User> users = new HashSet<>();
    }