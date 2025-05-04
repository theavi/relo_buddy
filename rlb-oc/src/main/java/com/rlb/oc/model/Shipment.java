package com.rlb.oc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

    public String orderId;
    public Location origin;
    public Location destination;
    public String status;

}
