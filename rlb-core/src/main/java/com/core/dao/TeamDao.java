package com.core.dao;

import com.core.model.Team;

import java.util.List;

public interface TeamDao {

    public List<Team> findAll();
    public Team save(Team team);
    public Team getTeamByPincodeAndAvailability(int pincode);
    public Team getTeamByPincode(int pincode);
}
