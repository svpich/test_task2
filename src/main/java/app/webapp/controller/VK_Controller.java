package app.webapp.controller;

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
    public VK_Controller(VK_ApiService vk_apiService) {
        this.vk_apiService = vk_apiService;
    }

    @GetMapping("/method2")
    public Set<GroupDTO> getBySubString(@RequestParam("subString")String subString) {
        return vk_apiService.findGroupsBySubstring(subString);
    }

    @GetMapping("/method1")
    public Set<GroupDTO> getByUserId(@RequestParam("userId")String userId) {
        return vk_apiService.findUserFriendsGroupsByUserId(userId);
    }

    @GetMapping("/method3")
    public Set<GroupDTO> getByUserId2(@RequestParam("userId")String userId, @RequestParam("subString")String subString) {
        return vk_apiService.findUserGroupsAndUserFriendsGroupsByUserIdAndGroupsBySubstring(userId, subString);
    }
}
