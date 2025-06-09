package com.core.mapper;

import com.core.dto.TeamDto;
import com.core.entity.Team;

public class TeamMapper {

    public static TeamDto toDto(Team team){
        TeamDto dto = new TeamDto();
        dto.setId(team.getId());
        dto.setWorkers(team.getWorkers());
        dto.setSize(team.getSize());
        dto.setPincode(team.getPincode());
        dto.setAllocated(team.getAllocated());
        return dto;
    }
}
