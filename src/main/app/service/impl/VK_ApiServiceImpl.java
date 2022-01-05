package app.service.impl;

import app.api.abstracts.VK_Api;
import app.service.abstracts.VK_ApiService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class VK_ApiServiceImpl implements VK_ApiService {

    private static final Logger logger = LoggerFactory.getLogger(VK_ApiServiceImpl.class);
    VK_Api vkAPI;

    @Autowired
    public VK_ApiServiceImpl(VK_Api vkAPI) {
        this.vkAPI = vkAPI;
    }

    @Override
    public List<String> findGroupBySubstring(String subString) {
        List<String> resultList = new ArrayList<>();

        HttpResponse response = vkAPI.findGroupBySubstring(subString);

        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            StringWriter content = new StringWriter();

            try {
                IOUtils.copy(response.getEntity().getContent(), content); //копируем данные из одного потока в другой
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject outerObject = new JSONObject(content.toString());

                if (outerObject.has("response")) {
                    JSONObject innerObject = outerObject.getJSONObject("response");
                    JSONArray itemsArray = innerObject.getJSONArray("items");
                    JSONObject item;

                    for (int i = 0; i < itemsArray.length(); i++) {
                        item = (JSONObject) itemsArray.get(i);

                        String s = (String) item.get("name");
                        String encodedString = new String(s.getBytes("Windows-1251"), StandardCharsets.UTF_8);
                        resultList.add(encodedString);
                    }
                    logger.info("Получен список групп по подстроке: " + subString);
                } else if (outerObject.has("error")) {
                    JSONObject innerObject = outerObject.getJSONObject("error");

                    String error_msg = (String) innerObject.get("error_msg");
                    logger.error("Не удалось получить список групп по подстроке: " + subString + ". Ошибка: " + error_msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // ОБРАБОТАТЬ ДРУГИЕ СТАТУСЫ
        return resultList;
    }

    @Override
    public List<String> findUserFriendsGroupsByUserId(String userId) {
        List<String> resultList = new ArrayList<>();
        List<Integer> userFriendList = findUserFriendsByUserId(userId);

        List<String> tempUserGroupList;
        for (Integer e : userFriendList) {
            tempUserGroupList = findUserGroupByUserId(Integer.toString(e));

            resultList.addAll(tempUserGroupList);
        }

        return resultList;
    }

    @Override
    public List<Integer> findUserFriendsByUserId(String userId) {
        List<Integer> resultList = new ArrayList<>();

        HttpResponse response = vkAPI.findUserFriendsByUserId(userId);

        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            StringWriter content = new StringWriter();

            try {
                IOUtils.copy(response.getEntity().getContent(), content); //копируем данные из одного потока в другой
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject outerObject = new JSONObject(content.toString());

                if (outerObject.has("response")) {
                    JSONObject innerObject = outerObject.getJSONObject("response");
                    JSONArray itemsArray = innerObject.getJSONArray("items");
                    JSONObject item;

                    for (int i = 0; i < itemsArray.length(); i++) {
                        item = (JSONObject) itemsArray.get(i);

                        Integer idUserFriend = (Integer) item.get("id");
                        resultList.add(idUserFriend);
                    }
                    logger.info("Получен список друзей пользователя с id: " + userId);
                } else if (outerObject.has("error")) {
                    JSONObject innerObject = outerObject.getJSONObject("error");

                    String error_msg = (String) innerObject.get("error_msg");
                    logger.error("Не удалось получить список друзей пользователя с id: " + userId + ". Ошибка: " + error_msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // ОБРАБОТАТЬ ДРУГИЕ СТАТУСЫ
        return resultList;
    }

    @Override
    public List<String> findUserGroupByUserId(String userId) {
        List<String> resultList = new ArrayList<>();

        HttpResponse response = vkAPI.findUserGroupByUserId(userId);

        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            StringWriter content = new StringWriter();

            try {
                IOUtils.copy(response.getEntity().getContent(), content); //копируем данные из одного потока в другой
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            try {
                JSONObject outerObject = new JSONObject(content.toString());
                if (outerObject.has("response")) {
                    JSONObject innerObject = outerObject.getJSONObject("response");
                    JSONArray itemsArray = innerObject.getJSONArray("items");
                    JSONObject item;

                    for (int i = 0; i < itemsArray.length(); i++) {
                        item = (JSONObject) itemsArray.get(i);

                        String s = (String) item.get("name");
                        String encodedString = new String(s.getBytes("Windows-1251"), StandardCharsets.UTF_8);
                        resultList.add(encodedString);
                    }
                    logger.info("Получен список групп пользователя с id: " + userId);
                } else if (outerObject.has("error")) {
                    JSONObject innerObject = outerObject.getJSONObject("error");

                    String error_msg = (String) innerObject.get("error_msg");
                    logger.error("Не удалось получить список групп пользователя с id: " + userId + ". Ошибка: " + error_msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // ОБРАБОТАТЬ ДРУГИЕ СТАТУСЫ
        return resultList;
    }
}
