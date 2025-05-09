package com.core;

import com.core.dao.UserDao;
import com.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RLBCoreRunner implements CommandLineRunner {

    @Autowired
    private UserDao userDao;
    @Override
    public void run(String... args) throws Exception {
        userDao.save(new User(1));
    }
}
