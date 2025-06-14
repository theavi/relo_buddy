package com.core.service;

import com.core.model.Role;
import com.core.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    public Role create(Role role);

    public void delete(Integer id);

    public Role update(Role role);
}