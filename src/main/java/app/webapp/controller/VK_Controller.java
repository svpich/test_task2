package app.webapp.controller;

import app.dao.abstracts.GroupDAO;
import app.model.dto.GroupDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import app.service.abstracts.VK_ApiService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Api(value = "/api")
@RestController
@RequestMapping("/api")
public class VK_Controller {

    VK_ApiService vk_apiService;

    @Autowired
    public VK_Controller(VK_ApiService vk_apiService, GroupDAO groupDAO) {
        this.vk_apiService = vk_apiService;
    }

    /**Выводит данные о профиле*/
    @ApiOperation(value = "Выводит данные о профиле")
    @GetMapping("/method1")
    public Set<GroupDTO> findUserGroupsAndUserFriendsGroupsByUserIdAndGroupsBySubstring
            (@RequestParam("userId")String userId,
             @RequestParam("subString")String subString) {
        Set<GroupDTO> responseSet = new HashSet<>(vk_apiService.findUserGroupsByUserId(userId));
        responseSet.addAll(vk_apiService.findUserFriendsGroupsByUserId(userId));
        responseSet.addAll(vk_apiService.findGroupsBySubstring(subString));

        return responseSet;
    }

    @PostMapping("/method2")
    public void findUserGroupsByUserIdAndGroupsBySubstringAndSaveToDB
            (@RequestParam("subString")String subString,
             @RequestParam("userId")String userId) {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<GroupDTO> groupDTOSet = new HashSet<>(vk_apiService.findUserGroupsByUserId(userId));
        groupDTOSet.addAll(vk_apiService.findGroupsBySubstring(subString));

        vk_apiService.saveCustomRequestToDB(localDateTime, userId, subString, groupDTOSet);
    }

    @GetMapping("/method3")
    public List<GroupDTO> findAllGroupFromDB() {
        return vk_apiService.findAllGroupFromDB();
    }
}
