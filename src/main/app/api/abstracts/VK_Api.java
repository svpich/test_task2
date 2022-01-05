package app.api.abstracts;

import org.apache.http.HttpResponse;

public interface VK_Api {
    HttpResponse findGroupBySubstring(String subString);
    HttpResponse findUserFriendsGroupsByUserId();
    HttpResponse findUserFriendsByUserId(String userId);
    HttpResponse findUserGroupByUserId(String userId);
}
