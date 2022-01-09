package app.dao.impl;

import app.dao.abstracts.GroupDAO;
import app.dao.util.impl.ReadWriteDAOImpl;
import app.model.entity.Group;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDAOImpl extends ReadWriteDAOImpl<Group> implements GroupDAO {
}
