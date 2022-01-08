package app.service.impl;

import app.api.abstracts.VK_Api;
import app.model.dto.GroupDTO;
import app.model.dto.UserDTO;
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
import java.util.*;

@Service
public class VK_ApiServiceImpl implements VK_ApiService {

    private static final Logger logger = LoggerFactory.getLogger(VK_ApiServiceImpl.class);
    VK_Api vkAPI;

    @Autowired
    public VK_ApiServiceImpl(VK_Api vkAPI) {
        this.vkAPI = vkAPI;
    }

    @Override
    public Set<GroupDTO> findGroupsBySubstring(String subString) {
        Set<GroupDTO> resultSet = new HashSet<>();

        HttpResponse response = vkAPI.findGroupsBySubstring(subString);

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

                        GroupDTO groupDTO = new GroupDTO();
                        groupDTO.setId((int) item.get("id"));
                        groupDTO.setName(new String(((String) item.get("name"))
                                .getBytes("Windows-1251"), StandardCharsets.UTF_8));
                        groupDTO.setIsClosed((int) item.get("is_closed"));
                        groupDTO.setType((String) item.get("type"));
                        groupDTO.setScreenName((String) item.get("screen_name"));
                        groupDTO.setPhoto_50((String) item.get("photo_50"));
                        groupDTO.setPhoto_100((String) item.get("photo_100"));
                        groupDTO.setPhoto_200((String) item.get("photo_200"));

                        resultSet.add(groupDTO);
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
        return resultSet;
    }

    @Override
    public Set<GroupDTO> findUserFriendsGroupsByUserId(String userId) {
        Set<GroupDTO> resultSet = new HashSet<>();
        Set<UserDTO> userFriendList = findUserFriendsByUserId(userId);

        Set<GroupDTO> tempUserGroupSet;
        for (UserDTO e : userFriendList) {
            tempUserGroupSet = findUserGroupsByUserId(Integer.toString(e.getId()));

            resultSet.addAll(tempUserGroupSet);
        }

        return resultSet;
    }

    @Override
    public Set<UserDTO> findUserFriendsByUserId(String userId) {
        Set<UserDTO> resultSet = new HashSet<>();

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

                        UserDTO userDTO = new UserDTO();
                        userDTO.setId((int) item.get("id"));
                        userDTO.setFirstName((new String(((String) item.get("first_name"))
                                .getBytes("Windows-1251"), StandardCharsets.UTF_8)));
                        userDTO.setFirstName((new String(((String) item.get("last_name"))
                                .getBytes("Windows-1251"), StandardCharsets.UTF_8)));

                        resultSet.add(userDTO);
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
        return resultSet;
    }

    @Override
    public Set<GroupDTO> findUserGroupsByUserId(String userId) {
        Set<GroupDTO> resultSet = new HashSet<>();

        HttpResponse response = vkAPI.findUserGroupsByUserId(userId);

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

                        GroupDTO groupDTO = new GroupDTO();
                        groupDTO.setId((int) item.get("id"));
                        groupDTO.setName(new String(((String) item.get("name"))
                                .getBytes("Windows-1251"), StandardCharsets.UTF_8));
                        groupDTO.setIsClosed((int) item.get("is_closed"));
                        groupDTO.setType((String) item.get("type"));
                        groupDTO.setScreenName((String) item.get("screen_name"));
                        groupDTO.setPhoto_50((String) item.get("photo_50"));
                        groupDTO.setPhoto_100((String) item.get("photo_100"));
                        groupDTO.setPhoto_200((String) item.get("photo_200"));

                        resultSet.add(groupDTO);
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
        return resultSet;
    }

    @Override
    // Получаем группы по подстроке и возвращаем в каких из них состоит пользователь или его друзья.
    public Set<GroupDTO> findUserGroupsAndUserFriendsGroupsByUserIdAndGroupsBySubstring(String userId, String subString) {
        Set<GroupDTO> userGroupsSet = findUserGroupsByUserId(userId);
        Set<GroupDTO> userFriendsGroupsSet = findUserFriendsGroupsByUserId(userId);
        Set<GroupDTO> groupsBySubstringSet = findGroupsBySubstring(subString);

        Set<GroupDTO> tempSet = new HashSet<>(userGroupsSet);
        tempSet.addAll(userFriendsGroupsSet);

        Set<GroupDTO> resultSet = new HashSet<>();
        boolean alreadyExist;

        for (GroupDTO e : tempSet) {
            alreadyExist = groupsBySubstringSet.add(e);

            if (!alreadyExist) {
                resultSet.add(e);
            }
        }
        logger.info("Кол-во групп в которых состоит пользователь или его друзья: " + resultSet.size());
        return resultSet;
    }
}

