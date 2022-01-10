package app.api.impl;

import app.api.abstracts.VK_Api;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VK_ApiImpl implements VK_Api {

    URIBuilder uriBuilder;
    final static String ACCESS_TOKEN = "a43e21197a2183078bb09d628cf390eb469b674dee0b4b8bc3bc7c10034702154b9317b88cd2c1087c984";
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
