package app.dao.abstracts;

import app.dao.util.abstracts.ReadWriteDAO;
import app.model.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserDAO extends ReadWriteDAO<User> {

    @Override
    Optional<User> findById(String id);

    @Override
    void persist(User user);

    @Override
    List<User> getAll();
}
