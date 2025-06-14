package com.core.service.impl;

import com.core.dao.UserDao;
import com.core.model.User;
import com.core.service.UserService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    public User create(User user) {
        return userDao.create(user);
    }

    public void delete(Integer id) {
        userDao.delete(id);
    }

    public User update(User user) {
        return userDao.update(user);
    }
}
