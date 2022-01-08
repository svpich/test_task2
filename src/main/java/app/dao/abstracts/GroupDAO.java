package app.dao.abstracts;

import app.dao.util.abstracts.ReadWriteDAO;
import app.model.entity.Group;

import java.util.Set;

public interface GroupDAO extends ReadWriteDAO<Group> {
    @Override
    void persist(Group group);

    @Override
    Set<Group> getAll();
}
