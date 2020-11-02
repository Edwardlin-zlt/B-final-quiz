package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.service.GroupService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    //TODO GTB-完成度: - GroupController.java:26 bug，无法正常调用
    //TODO GTB-知识点: - GroupController.java:27 按照restful实践，改变的资源，所以这里应该POST
    @GetMapping("/groups/auto-grouping")
    public List<Group> autoGrouping() {
        return groupService.autoGrouping();
    }
}
