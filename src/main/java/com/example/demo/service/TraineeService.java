package com.example.demo.service;

import com.example.demo.entity.Trainee;
import com.example.demo.exception.TraineeNotExistException;
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
        return traineeRepository.findGroupedTrainees();
    }

    public List<Trainee> findUngroupedTrainees() {
        return traineeRepository.findUngroupedTrainees();
    }

    public List<Trainee> findAllTrainees() {
        return traineeRepository.findAll();
    }

    public Trainee createNewTrainee(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    public void deleteTraineeById(Long trainee_id) throws TraineeNotExistException {
        traineeRepository.findById(trainee_id)
                .orElseThrow(() -> new TraineeNotExistException("Trainee didn't Exist"));

        traineeRepository.deleteById(trainee_id);
    }

}
