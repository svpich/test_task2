package app.webapp.controller;

import app.service.abstracts.VK_ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VK_Controller {

    VK_ApiService vk_apiService;

    @Autowired
    public VK_Controller(VK_ApiService vk_apiService) {
        this.vk_apiService = vk_apiService;
    }

    @GetMapping("/method2")
    public List<String> getBySubString(@RequestParam("subString")String subString) {
        return vk_apiService.findGroupBySubstring(subString);
    }

    @GetMapping("/method1")
    public List<String> getByUserId(@RequestParam("userId")String userId) {
        return vk_apiService.findUserFriendsGroupsByUserId(userId);
    }

    @GetMapping("/method3")
    public List<String> getByUserId2(@RequestParam("userId")String userId) {
        return vk_apiService.findUserGroupByUserId(userId);
    }
}
