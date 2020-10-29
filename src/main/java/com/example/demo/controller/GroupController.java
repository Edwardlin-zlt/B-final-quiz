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

    @GetMapping("/Groups")
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    @GetMapping("/Groups/auto-grouping")
    public List<Group> autoGrouping() {
        return groupService.autoGrouping();
    }
}
