package cn.edu.nju.user.dao.impl;

import cn.edu.nju.user.dao.BatchDao;
import entity.user.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDao implements BatchDao<User> {
    @PersistenceContext
    protected EntityManager em;

    @Transactional
    @Override
    public Iterable<User> batchSave(Iterable<User> users) {
        int i = 0;
        for (User user : users) {
            em.persist(user);
            i++;
            if (i % 30 == 0) {
                em.flush();
                em.clear();
            }
        }
        return users;
    }

    @Transactional
    @Override
    public Iterable<User> batchUpdate(Iterable<User> users) {
        int i = 0;
        for (User user : users) {
            em.merge(user);
            i++;
            if (i % 30 == 0) {
                em.flush();
                em.clear();
            }
        }
        return users;
    }
}
