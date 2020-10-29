package com.example.demo.service;

import com.example.demo.repository.TraineeRepository;
import org.springframework.stereotype.Service;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }
}
