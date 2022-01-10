package app.webapp.controller;

import app.dao.abstracts.GroupDAO;
import app.model.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import app.service.abstracts.VK_ApiService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class VK_Controller {

    VK_ApiService vk_apiService;

    @Autowired
    public VK_Controller(VK_ApiService vk_apiService, GroupDAO groupDAO) {
        this.vk_apiService = vk_apiService;
    }

    @GetMapping("/method1")
    public Set<GroupDTO> findUserGroupsAndUserFriendsGroupsByUserIdAndGroupsBySubstring
            (@RequestParam("userId")String userId,
             @RequestParam("subString")String subString) {
        Set<GroupDTO> responseSet = new HashSet<>(vk_apiService.findUserGroupsByUserId(userId));
        responseSet.addAll(vk_apiService.findUserFriendsGroupsByUserId(userId));
        responseSet.addAll(vk_apiService.findGroupsBySubstring(subString));

        return responseSet;
    }

    @GetMapping("/method2")
    public void findUserGroupsByUserIdAndGroupsBySubstringAndSaveToDB
            (@RequestParam("subString")String subString,
             @RequestParam("userId")String userId) {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<GroupDTO> groupDTOSet = new HashSet<>(vk_apiService.findUserGroupsByUserId(userId));
        groupDTOSet.addAll(vk_apiService.findGroupsBySubstring(subString));

        vk_apiService.saveCustomRequestToDB(localDateTime, userId, subString, groupDTOSet);
    }

    @GetMapping("/method3")
    public void getByUserId(@RequestParam("userId")String userId) {
        vk_apiService.findUserByUserId(userId);
    }
}
