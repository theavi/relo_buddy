package com.core.service;

import com.core.entity.Role;
import com.core.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;  // DAO using Hibernate to access the database

    public Role create(Role role) {
        return roleDao.create(role);  // Using Hibernate DAO to save the role
    }

    public void delete(Integer id) {
        roleDao.delete(id);  // Using Hibernate DAO to delete the role
    }

    public Role update(Role role) {
        return roleDao.update(role);  // Using Hibernate DAO to update the role
    }
}