package app.api.impl;

import app.api.abstracts.Api;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VK_Api implements Api {

    URIBuilder uriBuilder;
    final static String ACCESS_TOKEN = "059223d3a407c160534593c5572a0e6fe042507584555a69840a8cdd02c45c56c146d951fff77f81a7f3e";
    final static String PROTOCOL = "https";
    final static String HOST = "api.vk.com";
    final static String API_VERSION = "5.131";

    @Autowired
    public VK_Api(URIBuilder uriBuilder) {
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
                .setParameter("count", "20")
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
