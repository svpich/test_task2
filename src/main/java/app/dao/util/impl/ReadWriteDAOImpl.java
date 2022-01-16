package app.dao.util.impl;

import app.dao.util.abstracts.ReadWriteDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;


public class ReadWriteDAOImpl<E> implements ReadWriteDAO<E> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<E> clazz;

    @SuppressWarnings("unchecked")
    public ReadWriteDAOImpl() {
        this.clazz = (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public void persist(E e) {
        entityManager.persist(e);
    }

//    @Override
//    public List<E> findAll() {
//        return entityManager.createQuery("from " + clazz.getName()).getResultList();
//    }

//    @Override
//    public Optional<E> findById(String id) {
//        return entityManager.createQuery("select c from " + clazz.getName() + " c where c.id = :id")
//                .setParameter("id", id).getResultList().stream().findAny();
//    }


}
