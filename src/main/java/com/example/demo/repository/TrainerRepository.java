package com.example.demo.repository;

import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query("select t from Trainer t where t.group is null")
    List<Trainer> findUngroupedTrainers();

    @Query("select t from Trainer t where t.group is not null")
    List<Trainer> findGroupedTrainers();
}
