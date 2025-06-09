package com.core.dto;

import com.core.entity.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    private Integer id;
    private List<Worker> workers;
    private Integer size;
    private Integer pincode;
    private boolean isAllocated;
    private String orderId;
}
