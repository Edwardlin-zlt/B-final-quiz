package com.example.demo.controller;

import com.example.demo.entity.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TraineeController {

    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping("/trainees")
    public String test() {
        return "Hello";
    }

    @GetMapping("/trainees")
    public List<Trainee> getTrainees(@RequestParam(value = "grouped", required = false) Optional<Boolean> isGrouped) {
        if (isGrouped.isPresent()) {
            if (isGrouped.get()) {
                return traineeService.findGroupedTrainees();
            } else {
                return traineeService.findUngroupedTrainees();
            }
        }
        return traineeService.findAllTrainees();
    }

}
