package com.rlb.oc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    public String name;
    public float latitude;
    public float longitude;
    public String Address;
}
