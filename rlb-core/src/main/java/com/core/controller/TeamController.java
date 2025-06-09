package com.core.controller;

import com.core.dto.TeamDto;
import com.core.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("/getTeam/{pincode}")
    public TeamDto getTeam(@PathVariable("pincode") Integer pincode){
        return teamService.getTeam(pincode);
    }
}
