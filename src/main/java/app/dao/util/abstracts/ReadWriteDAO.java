package app.dao.util.abstracts;

import java.util.Optional;
import java.util.Set;

public interface ReadWriteDAO<E> {
    void persist(E e);
    Set<E> getAll();
    Optional<E> getById(String id);
}
