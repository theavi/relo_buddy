package com.rlb.oc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "dimension")
@Data
@AllArgsConstructor

public class Dimension {

    private Integer length;// ObjectId-- String
    private Integer breadth;
    private Integer height;
}

