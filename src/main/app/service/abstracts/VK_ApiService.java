package app.service.abstracts;

import org.apache.http.HttpResponse;

import java.util.List;

public interface VK_ApiService {
    List<String> findGroupBySubstring(String subString);
    List<String> findUserFriendsGroupsByUserId(String userId);
    List<Integer> findUserFriendsByUserId(String userId);
    List<String> findUserGroupByUserId(String userId);
}
