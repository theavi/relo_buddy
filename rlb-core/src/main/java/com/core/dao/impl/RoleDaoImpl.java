package com.core.dao.impl;

import com.core.dao.RoleDao;
import com.core.entity.Role;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role create(Role role) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(role);  // Hibernate save
        transaction.commit();
        session.close();
        return role;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Role role = session.get(Role.class, id);  // Retrieve the Role by id
        if (role != null) {
            session.delete(role);  // Hibernate delete
        }
        transaction.commit();
        session.close();
    }

    @Override
    public Role update(Role role) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(role);  // Hibernate update
        transaction.commit();
        session.close();
        return role;
    }
}