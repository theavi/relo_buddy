package com.rlb.api.service.impl;

import com.rlb.api.model.User;
import com.rlb.api.repository.UserRepository;
import com.rlb.common.exception.RecordNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByName(username).orElseThrow(()->new RecordNotFound("User not exist"));

        System.out.println("User: " + user.getName());
        System.out.println("Roles count: " + user.getRoles().size());
        user.getRoles().forEach(role -> System.out.println("Role: " + role.getName()));

        Set<GrantedAuthority> authority=user.getRoles().stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());


        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authority);
    }
}
