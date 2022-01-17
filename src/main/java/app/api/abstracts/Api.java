package app.api.abstracts;

import org.apache.http.HttpResponse;


public interface Api {
    HttpResponse findGroupsBySubstring(String subString);
    HttpResponse findUserFriendsByUserId(String userId);
    HttpResponse findUserGroupsByUserId(String userId);
    HttpResponse findUserByUserId(String userId);
}
