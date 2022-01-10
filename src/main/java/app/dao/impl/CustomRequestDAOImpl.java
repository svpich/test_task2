package app.dao.impl;

import app.dao.abstracts.CustomRequestDAO;
import app.dao.util.impl.ReadWriteDAOImpl;
import app.model.entity.CustomRequest;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRequestDAOImpl extends ReadWriteDAOImpl<CustomRequest> implements CustomRequestDAO {
}
