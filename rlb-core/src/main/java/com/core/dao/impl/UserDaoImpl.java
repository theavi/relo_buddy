package com.core.dao.impl;

import com.core.dao.UserDao;
import com.core.model.Role;
import com.core.model.User;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User create(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                Role persistentRole = session.get(Role.class, role.getId());
                if (persistentRole != null) {
                    persistentRole.getUsers().add(user); // maintain bidirectional relationship
                }
            }
        }
        session.merge(user);  // Hibernate save
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);  // Retrieve the User by id

        if (user != null) {
            for (Role role : user.getRoles()) {
                role.getUsers().remove(user); // remove link from Role side
                session.update(role);         // persist role update
            }
            session.delete(user);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public User update(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                Role persistentRole = session.get(Role.class, role.getId());
                if (persistentRole != null) {
                    persistentRole.getUsers().add(user);
                }
            }
        }
        session.merge(user);  // Hibernate update
        transaction.commit();
        session.close();
        return user;
    }
}
