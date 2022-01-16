package app.dao.abstracts;

import app.dao.util.abstracts.ReadWriteDAO;
import app.model.entity.CustomRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomRequestDAO extends PagingAndSortingRepository<CustomRequest, Long> {
}
