package app.webapp.controller;

import app.model.dto.GroupDTO;
import app.service.abstracts.VK_ApiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class VK_Controller {

    VK_ApiService vk_apiService;

    @Autowired
    public VK_Controller(VK_ApiService vk_apiService) {
        this.vk_apiService = vk_apiService;
    }

    /**Выводит данные о профиле джава док*/
    @ApiOperation(value = "Возвращает группы(сообщества) по указанной подстроке и (одновременно)" +
            "в которые входит указанный пользователь или его друзья.")
    @GetMapping("/method1")
    public ResponseEntity<Set<GroupDTO>> findUserGroupsAndUserFriendsGroupsByUserIdAndGroupsBySubstring
            (@RequestParam("userId")String userId,
             @RequestParam("subString")String subString) {
        Set<GroupDTO> responseSet = new HashSet<>(vk_apiService.findUserGroupsByUserId(userId));
        responseSet.addAll(vk_apiService.findUserFriendsGroupsByUserId(userId));
        responseSet.addAll(vk_apiService.findGroupsBySubstring(subString));

        return new ResponseEntity<>(responseSet, HttpStatus.OK);
    }

    @ApiOperation(value = "Осуществляет поиск групп(сообществ) по подстроке и (одновременно)" +
            "в которые входит указанный пользователь. Результат сохраняет в Базу Данных.")
    @PostMapping("/method2")
    public ResponseEntity<?> findUserGroupsByUserIdAndGroupsBySubstringAndSaveToDB
            (@RequestParam("subString")String subString,
             @RequestParam("userId")String userId) {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<GroupDTO> groupDTOSet = new HashSet<>(vk_apiService.findUserGroupsByUserId(userId));
        groupDTOSet.addAll(vk_apiService.findGroupsBySubstring(subString));

        vk_apiService.saveCustomRequestToDB(localDateTime, userId, subString, groupDTOSet);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Возвращает все когда либо найденныйе с использованием \"/method2\" группы(сообщества).")
    @GetMapping("/method3")
    public ResponseEntity<?> findAllGroupFromDB(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);

        return new ResponseEntity<>(vk_apiService.findAllGroupWithPagination(paging), HttpStatus.OK);
    }
}
