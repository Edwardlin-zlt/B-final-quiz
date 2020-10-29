package com.example.demo.controller;

import com.example.demo.entity.Trainee;
import com.example.demo.exception.TraineeNotExistException;
import com.example.demo.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/trainees")
    @ResponseStatus(HttpStatus.CREATED)
    public Trainee createNewTrainee(@RequestBody @Valid Trainee trainee) {
        return traineeService.createNewTrainee(trainee);
    }

    @DeleteMapping("/trainees/{trainee_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainee(@PathVariable("trainee_id") Long trainee_id) throws TraineeNotExistException {
        traineeService.deleteTraineeById(trainee_id);
    }

}
