package app.service.abstracts;

import app.model.dto.GroupDTO;
import app.model.dto.UserDTO;
import app.model.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Set;

public interface VK_ApiService {
    UserDTO findUserByUserId(String userId);

    Set<GroupDTO> findGroupsBySubstring(String subString);

    Set<GroupDTO> findUserFriendsGroupsByUserId(String userId);

    Set<UserDTO> findUserFriendsByUserId(String userId);

    Set<GroupDTO> findUserGroupsByUserId(String userId);

    Page<GroupDTO> findAllGroupWithPagination(Pageable pageable);

    void saveCustomRequestToDB(LocalDateTime localDateTime,
                               String userId, String subString,
                               Set<GroupDTO> groupDTOSet);
}
