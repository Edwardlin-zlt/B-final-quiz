package com.example.demo.controller;

import com.example.demo.service.TraineeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TraineeController {

    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }
}
