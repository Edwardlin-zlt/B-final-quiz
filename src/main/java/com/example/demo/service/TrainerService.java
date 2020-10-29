package com.example.demo.service;

import com.example.demo.entity.Trainer;
import com.example.demo.exception.TrainerNotExistException;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public List<Trainer> findGroupedTrainers() {
        return trainerRepository.findGroupedTrainers();
    }

    public List<Trainer> findUngroupedTrainers() {
        return trainerRepository.findUngroupedTrainers();
    }

    public List<Trainer> findAllTrainers() {
        return trainerRepository.findAll();
    }

    public Trainer createNewTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public void deleteTrainerById(Long Trainer_id) throws TrainerNotExistException {
        trainerRepository.findById(Trainer_id)
                .orElseThrow(() -> new TrainerNotExistException("Trainer didn't Exist"));

        trainerRepository.deleteById(Trainer_id);
    }
}
