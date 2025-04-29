package com.rlb.oc;

import com.rlb.oc.model.Role;
import com.rlb.oc.model.User;
import com.rlb.oc.repository.RoleRepository;
import com.rlb.oc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
//@Import(SharedConfig.class)
public class RlbOcApplication {



    public static void main(String[] args) {
        SpringApplication.run(RlbOcApplication.class, args);


    }
}