package app.api.impl;

import app.api.abstracts.VK_Api;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VK_ApiImpl implements VK_Api {

    URIBuilder uriBuilder;
    final static String ACCESS_TOKEN = "bc8ae6c17c8d9122d1375d6f6ec8b12269c6ba38853caaa50a79f9155f1435fd6e87480de3bba1d478441";
    final static String PROTOCOL = "https";
    final static String HOST = "api.vk.com";
    final static String API_VERSION = "5.131";

    @Autowired
    public VK_ApiImpl (URIBuilder uriBuilder) {
        this.uriBuilder = uriBuilder;
    }

    @Override
    public HttpResponse findGroupsBySubstring(String subString) {
        uriBuilder.setScheme(PROTOCOL).setHost(HOST).setPath("/method/groups.search")
                .setParameter("q", subString)
                .setParameter("v", API_VERSION)
                .setParameter("access_token", ACCESS_TOKEN);

        return HttpConnectionAgent.connectResponse(uriBuilder);
    }

    @Override
    public HttpResponse findUserFriendsGroupsByUserId() {
        return null;
    }

    @Override
    public HttpResponse findUserFriendsByUserId(String userId) {
        uriBuilder.setScheme(PROTOCOL).setHost(HOST).setPath("/method/friends.get")
                .setParameter("user_id", userId)
                .setParameter("fields", "id")
                .setParameter("v", API_VERSION)
                .setParameter("access_token", ACCESS_TOKEN);

        return HttpConnectionAgent.connectResponse(uriBuilder);
    }

    @Override
    public HttpResponse findUserGroupsByUserId(String userId) {
        uriBuilder.setScheme(PROTOCOL).setHost(HOST).setPath("/method/groups.get")
                .setParameter("user_id", userId)
                .setParameter("extended", "1")
                .setParameter("v", API_VERSION)
                .setParameter("access_token", ACCESS_TOKEN);

        return HttpConnectionAgent.connectResponse(uriBuilder);
    }

    @Override
    public HttpResponse findUserByUserId(String userId) {
        uriBuilder.setScheme(PROTOCOL).setHost(HOST).setPath("/method/users.get")
                .setParameter("user_id", userId)
                .setParameter("v", API_VERSION)
                .setParameter("access_token", ACCESS_TOKEN);

        return HttpConnectionAgent.connectResponse(uriBuilder);
    }
}
