package app.dao.util.abstracts;

import java.util.List;
import java.util.Optional;

public interface ReadWriteDAO<E> {

    void persist(E e);
//    List<E> findAll();
//    Optional<E> findById(String id);
}
