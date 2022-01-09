package app.dao.abstracts;

import app.dao.util.abstracts.ReadWriteDAO;
import app.model.entity.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDAO extends ReadWriteDAO<Group> {

    @Override
    void persist(Group group);

    @Override
    List<Group> getAll();

    @Override
    Optional<Group> findById(String id);
}
