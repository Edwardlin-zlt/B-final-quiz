package com.example.demo.controller;

import com.example.demo.entity.Trainer;
import com.example.demo.exception.TrainerNotExistException;
import com.example.demo.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/trainers")
    public List<Trainer> getTrainers(@RequestParam(value = "grouped", required = false) Optional<Boolean> isGrouped) {
        if (isGrouped.isPresent()) {
            if (isGrouped.get()) {
                return trainerService.findGroupedTrainers();
            } else {
                return trainerService.findUngroupedTrainers();
            }
        }
        return trainerService.findAllTrainers();
    }

    @PostMapping("/trainers")
    @ResponseStatus(HttpStatus.CREATED)
    public Trainer createNewTrainer(@RequestBody @Valid Trainer trainer) {
        return trainerService.createNewTrainer(trainer);
    }

    //TODO GTB-完成度: - TrainerController.java:40 bug，无法正常调用
    @DeleteMapping("/trainers/{trainer_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainer(@PathVariable("trainer_id") Long trainer_id) throws TrainerNotExistException {
        trainerService.deleteTrainerById(trainer_id);
    }
}
