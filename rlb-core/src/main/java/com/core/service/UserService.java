package com.core.service;

import com.core.dao.UserDao;
import com.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;  // DAO using Hibernate to access the database

    public User create(User user) {
        return userDao.create(user);  // Using Hibernate DAO to save the user
    }

    public void delete(Integer id) {
        userDao.delete(id);  // Using Hibernate DAO to delete the user
    }

    public User update(User user) {
        return userDao.update(user);  // Using Hibernate DAO to update the user
    }
}
