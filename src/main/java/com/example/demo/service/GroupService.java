package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    public GroupService(GroupRepository groupRepository, TraineeRepository traineeRepository, TrainerRepository trainerRepository) {
        this.groupRepository = groupRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

//    @Transactional
    public List<Group> autoGrouping() {
        groupRepository.deleteAll();
        List<Group> groups = autoGenerateGroups();
        autoGroupTrainees(groups);
        autoGroupTrainers(groups);
        groupRepository.saveAll(groups);
        return groups;
    }

    private void autoGroupTrainers(List<Group> groups) {
        List<Trainer> trainers = trainerRepository.findAll();
        Collections.shuffle(trainers);

        for (int i = 0; i < trainers.size(); i++) {
            int groupIndex = i % groups.size();
            groups.get(groupIndex).getTrainer().add(trainers.get(i));
        }
    }

    private List<Group> autoGenerateGroups() {
        List<Trainer> trainers = trainerRepository.findAll();
        int groupSize = trainers.size() / 2;
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < groupSize; i++) {
            groups.add(new Group());
        }
        return groups;
    }

    public void autoGroupTrainees(List<Group> groups) {
        List<Trainee> trainees = traineeRepository.findAll();
        Collections.shuffle(trainees);

        for (int i = 0; i < trainees.size(); i++) {
            int groupIndex = i % groups.size();
            groups.get(groupIndex).getTrainee().add(trainees.get(i));
        }
    }

}
