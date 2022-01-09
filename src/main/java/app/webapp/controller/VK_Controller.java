package app.webapp.controller;

import app.dao.abstracts.GroupDAO;
import app.dao.abstracts.UserDAO;
import app.model.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import app.service.abstracts.VK_ApiService;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class VK_Controller {

    VK_ApiService vk_apiService;

    @Autowired
    public VK_Controller(VK_ApiService vk_apiService, UserDAO userDAO, GroupDAO groupDAO) {
        this.vk_apiService = vk_apiService;
    }

    @GetMapping("/method1")
    public Set<GroupDTO> findUserGroupsAndUserFriendsGroupsByUserIdAndGroupsBySubstring
            (@RequestParam("userId")String userId, @RequestParam("subString")String subString) {

        return vk_apiService.findUserGroupsAndUserFriendsGroupsByUserIdAndGroupsBySubstring(userId, subString);
    }

    @GetMapping("/method2")
    public Set<GroupDTO> getBySubString(@RequestParam("subString")String subString) {
        return vk_apiService.findGroupsBySubstring(subString);
    }

    @GetMapping("/method3")
    public void getByUserId(@RequestParam("userId")String userId) {
        vk_apiService.saveGroupToDB(userId);
    }
}
