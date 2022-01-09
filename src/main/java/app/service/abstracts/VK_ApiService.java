package app.service.abstracts;

import app.model.dto.GroupDTO;
import app.model.dto.UserDTO;

import java.util.Set;

public interface VK_ApiService {
    UserDTO findUserByUserId(String userId);
    Set<GroupDTO> findGroupsBySubstring(String subString);
    Set<GroupDTO> findUserFriendsGroupsByUserId(String userId);
    Set<UserDTO> findUserFriendsByUserId(String userId);
    Set<GroupDTO> findUserGroupsByUserId(String userId);
    Set<GroupDTO> findUserGroupsAndUserFriendsGroupsByUserIdAndGroupsBySubstring(String userId, String subString);
    void saveGroupToDB(String userId);
    void saveUserToDB(String userId);
}
