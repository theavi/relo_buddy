package com.core.service.impl;

import com.core.dao.RoleDao;
import com.core.model.Role;
import com.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role create(Role role) {
        return roleDao.create(role);
    }

    public void delete(Integer id) {
         roleDao.delete(id);
    }

    public Role update(Role role) {
         return roleDao.update(role);
    }
}
