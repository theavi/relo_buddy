package com.core.dao;

import com.core.model.User;

public interface UserDao {
    User create(User user);
    void delete(Integer id);
    User update(User user);
}
