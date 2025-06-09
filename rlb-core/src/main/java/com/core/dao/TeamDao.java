package com.core.dao;

import com.core.entity.Team;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface TeamDao {

    public List<Team> findAll();
    public Team save(Team team);
    public Team getTeamByPincodeAndAvailability(int pincode);
}
