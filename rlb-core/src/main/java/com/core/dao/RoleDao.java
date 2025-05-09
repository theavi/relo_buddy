package com.core.dao;

import com.core.model.Role;

public interface RoleDao {
    Role create(Role role);
    void delete(Integer id);
    Role update(Role role);
}
