package com.rlb.oc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Document(collation = "user")
public class User {

    @Id
    private String id;
    private String name;
    @DBRef
    private List<Role> roles = new ArrayList<>();

    public User() {
        this.id = UUID.randomUUID().toString();
    }
}
