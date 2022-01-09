package app.dao.impl;

import app.dao.abstracts.UserDAO;
import app.dao.util.impl.ReadWriteDAOImpl;
import app.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends ReadWriteDAOImpl<User> implements UserDAO {


}
