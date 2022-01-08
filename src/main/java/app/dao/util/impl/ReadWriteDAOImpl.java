package app.dao.util.impl;

import app.dao.util.abstracts.ReadWriteDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Repository
public class ReadWriteDAOImpl<E> implements ReadWriteDAO<E> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void persist(E e) {
        entityManager.persist(e);
    }

    @Override
    public Set<E> getAll() {
        return null;
    }
}
