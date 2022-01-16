package app.service.impl;

import app.api.abstracts.VK_Api;
import app.converter.CustomRequestMapper;
import app.converter.GroupMapper;
import app.dao.abstracts.CustomRequestDAO;

import app.dao.abstracts.GroupDAO;
import app.model.dto.CustomRequestDTO;
import app.model.dto.GroupDTO;
import app.model.dto.UserDTO;
import app.model.entity.CustomRequest;
import app.model.entity.Group;
import app.service.abstracts.VK_ApiService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VK_ApiServiceImpl implements VK_ApiService {

    private static final Logger logger = LoggerFactory.getLogger(VK_ApiServiceImpl.class);
    private final VK_Api vkAPI;
    private final CustomRequestDAO customRequestDAO;
    private final CustomRequestMapper customRequestMapper;
    private final GroupDAO groupDAO;
    private final GroupMapper groupMapper;

    @Autowired
    public VK_ApiServiceImpl(VK_Api vkAPI,
                             CustomRequestDAO customRequestDAO,
                             CustomRequestMapper customRequestMapper,
                             GroupDAO groupDAO, GroupMapper groupMapper) {
        this.vkAPI = vkAPI;
        this.customRequestDAO = customRequestDAO;
        this.customRequestMapper = customRequestMapper;
        this.groupDAO = groupDAO;
        this.groupMapper = groupMapper;
    }

    @Override
    public Set<GroupDTO> findGroupsBySubstring(String subString) {
        Set<GroupDTO> resultSet = new HashSet<>();
        HttpResponse response = vkAPI.findGroupsBySubstring(subString);
        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            StringWriter content = new StringWriter();

            try {
                IOUtils.copy(response.getEntity().getContent(), content);
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
                        groupDTO.setGroupId((int) item.get("id"));
                        groupDTO.setName((String) item.get("name"));
                        groupDTO.setIsClosed((int) item.get("is_closed"));
                        groupDTO.setType((String) item.get("type"));
                        groupDTO.setScreenName((String) item.get("screen_name"));
                        groupDTO.setPhoto_50((String) item.get("photo_50"));
                        groupDTO.setPhoto_100((String) item.get("photo_100"));
                        groupDTO.setPhoto_200((String) item.get("photo_200"));

                        resultSet.add(groupDTO);
                    }
                    logger.info("Получен список групп (" + itemsArray.length() + "шт.) по подстроке: " + subString);
                } else if (outerObject.has("error")) {
                    JSONObject innerObject = outerObject.getJSONObject("error");

                    String error_msg = (String) innerObject.get("error_msg");
                    logger.error("Не удалось получить список групп по подстроке: " + subString + ". Ошибка: " + error_msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                IOUtils.copy(response.getEntity().getContent(), content);
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
                        userDTO.setLastName((new String(((String) item.get("last_name"))
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
        }
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
                IOUtils.copy(response.getEntity().getContent(), content);
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
                        groupDTO.setGroupId((int) item.get("id"));
                        groupDTO.setName((String) item.get("name"));
                        groupDTO.setIsClosed((int) item.get("is_closed"));
                        groupDTO.setType((String) item.get("type"));
                        groupDTO.setScreenName((String) item.get("screen_name"));
                        groupDTO.setPhoto_50((String) item.get("photo_50"));
                        groupDTO.setPhoto_100((String) item.get("photo_100"));
                        groupDTO.setPhoto_200((String) item.get("photo_200"));

                        resultSet.add(groupDTO);
                    }
                    logger.info("Получен список групп (" + itemsArray.length() + "шт.) пользователя с id: " + userId);
                } else if (outerObject.has("error")) {
                    JSONObject innerObject = outerObject.getJSONObject("error");

                    String error_msg = (String) innerObject.get("error_msg");
                    logger.error("Не удалось получить список групп пользователя с id: " + userId + ". Ошибка: " + error_msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultSet;
    }

    @Override
    public UserDTO findUserByUserId(String userId) {
        HttpResponse response = vkAPI.findUserByUserId(userId);
        int status = response.getStatusLine().getStatusCode();
        UserDTO userDTO = new UserDTO();

        if (status == 200) {
            StringWriter content = new StringWriter();

            try {
                IOUtils.copy(response.getEntity().getContent(), content);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject outerObject = new JSONObject(content.toString());

                if (outerObject.has("response")) {
                    JSONArray itemsArray = outerObject.getJSONArray("response");
                    JSONObject item = (JSONObject) itemsArray.get(0);

                    userDTO.setId((int) item.get("id"));
                    userDTO.setFirstName(((String) item.get("first_name")));
                    userDTO.setLastName(((String) item.get("last_name")));

                    logger.info("Получен пользователь c id: " + userDTO.getId());
                } else if (outerObject.has("error")) {
                    JSONObject innerObject = outerObject.getJSONObject("error");

                    String error_msg = (String) innerObject.get("error_msg");
                    logger.error("Не удалось получить пользователя с id: " + userId + ". Ошибка: " + error_msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userDTO;
    }

    @Transactional
    public void saveCustomRequestToDB(LocalDateTime localDateTime,
                                      String userId, String subString,
                                      Set<GroupDTO> groupDTOSet) {
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();

        CustomRequestDTO customRequestDTO = new CustomRequestDTO();
        customRequestDTO.setDate(localDate);
        customRequestDTO.setTime(localTime);
        customRequestDTO.setParamUserId(userId);
        customRequestDTO.setParamSubstring(subString);
        customRequestDTO.setGroupSet(groupDTOSet);

        CustomRequest customRequest = customRequestMapper.customRequestDtoToCustomRequest(customRequestDTO);

        customRequestDAO.save(customRequest);
        logger.info("Запрос добавлен в БД.");
    }

    public Page<GroupDTO> findAllGroupWithPagination(Pageable pageable) {
        Set<GroupDTO> set = new HashSet<>();
                groupDAO.findAll().forEach(e -> set.add(groupMapper.groupToGroupDTO(e)));
        List<GroupDTO> list = new ArrayList<>(set);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        if(start > list.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        }

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}

