package com.rlb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    public String name;
    public String address;
    public String city;
    public String state;
    public String zip;
}
