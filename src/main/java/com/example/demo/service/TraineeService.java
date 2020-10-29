package com.example.demo.service;

import com.example.demo.entity.Trainee;
import com.example.demo.repository.TraineeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public List<Trainee> findGroupedTrainees() {
        return null;
    }

    public List<Trainee> findUngroupedTrainees() {
        return null;
    }

    public List<Trainee> findAllTrainees() {
        return null;
    }
}
