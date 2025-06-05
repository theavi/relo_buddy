package com.rlb.api.service;

import com.rlb.api.dto.LoginDto;

public interface AuthService {

    public String login(LoginDto dto);
}
