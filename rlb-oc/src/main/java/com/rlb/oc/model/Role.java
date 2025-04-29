package com.rlb.oc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@Data
@Document(collation = "role")
public class Role {

    @Id
    private String id;
    private String name;

    public Role() {
        this.id = UUID.randomUUID().toString();
    }
}
