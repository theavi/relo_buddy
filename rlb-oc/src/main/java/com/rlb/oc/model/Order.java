package com.rlb.oc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collation = "order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Integer id;
    private Date orderDate;
}
