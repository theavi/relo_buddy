package com.core.service.impl;

import com.core.dao.TeamDao;
import com.core.dto.TeamDto;
import com.core.entity.Team;
import com.core.mapper.TeamMapper;
import com.core.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamDao teamDao;

    @Override
    public TeamDto getTeam(Integer pincode) {
        Team team = teamDao.getTeamByPincodeAndAvailability(pincode);
        if(team != null){
            return TeamMapper.toDto(team);
        }
        return null;
    }
}
