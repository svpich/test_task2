package app.dao.util.impl;

import app.dao.util.abstracts.ReadWriteDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ReadWriteDAOImpl<E> implements ReadWriteDAO<E> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<E> clazz;

    @Override
    public void persist(E e) {
        entityManager.persist(e);
    }

    @Override
    public List<E> getAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();    }

    @Override
    public Optional<E> findById(String id) {
        return entityManager.createQuery("select c from " + clazz.getName() + " c where c.id = :id")
                .setParameter("id", id).getResultList().stream().findAny();
    }
}
