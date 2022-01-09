package app.api.abstracts;

import org.apache.http.HttpResponse;

public interface VK_Api {
    HttpResponse findGroupsBySubstring(String subString);
    HttpResponse findUserFriendsGroupsByUserId();
    HttpResponse findUserFriendsByUserId(String userId);
    HttpResponse findUserGroupsByUserId(String userId);
    HttpResponse findUserByUserId(String userId);
}
