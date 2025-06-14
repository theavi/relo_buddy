package com.core.service;

import com.core.dao.UserDao;
import com.core.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User create(User user);

    public void delete(Integer id);

    public User update(User user);
}
