package App.VKontakte;

import App.VKontakte.HttpConnectionAgent;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;

@Component
public class VKScribe {
// создать контроллер и в нем реализовать эту логику
    @Autowired
    URIBuilder uriBuilder;

    final static String ACCESS_TOKEN = "36902a0b7c7aa44af73cf34f7373f9880ceb0f4c59857a5e1dc4bd335bb5838c4ecdc4876736d29c39762";

    public void vkScribe() {
        uriBuilder.setScheme("https").setHost("api.vk.com").setPath("/method/groups.search")
                .setParameter("q", "фильмы")
                .setParameter("v", "5.81")
                .setParameter("access_token", ACCESS_TOKEN)
                .setParameter("count", "20");

        HttpResponse response = HttpConnectionAgent.connectResponse(uriBuilder);
        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            StringWriter content = new StringWriter();

            try {
                IOUtils.copy(response.getEntity().getContent(), content);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            try {
                JSONObject outerObject = new JSONObject(content.toString());
                JSONObject innerObject = outerObject.getJSONObject("response");
                JSONArray resultArray = innerObject.getJSONArray("items");
                JSONObject item = null;

                for (int i = 1; i < resultArray.length(); i++) {
                    item = (JSONObject) resultArray.get(i);
                    System.out.println(item.get("name"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
