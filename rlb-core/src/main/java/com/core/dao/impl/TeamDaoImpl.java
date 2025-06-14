package com.core.dao.impl;

import com.core.dao.TeamDao;
import com.core.model.Team;
import com.rlb.exception.RecordNotFound;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamDaoImpl implements TeamDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Team> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Team> teams = session.createQuery("from Team", Team.class).getResultList();
        tx.commit();
        session.close();
        return teams;
    }

    @Override
    public Team save(Team team) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(team);
        tx.commit();
        session.close();
        return team;
    }

    @Override
    public Team getTeamByPincodeAndAvailability(int pincode) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Team team = session.createQuery(
                 "FROM Team WHERE pincode= :pincode AND isAllocated = false", Team.class)
                    .setParameter("pincode", pincode)
                    .setMaxResults(1)
                    .uniqueResult();
        if(null == team){
            throw new RecordNotFound("Team not found");
        }
        team.setAllocated(true);
        tx.commit();
        session.close();
        return team;
    }

    @Override
    public Team getTeamByPincode(int pincode) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Team team = session.createQuery(
                "FROM Team where pincode: pincode", Team.class)
                .setParameter("pincode", pincode)
                .setMaxResults(1)
                .uniqueResult();

        if(null == team){
            throw new RecordNotFound("Team not found");
        }
        tx.commit();
        session.close();
        return team;
    }


}
