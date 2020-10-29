package com.example.demo.repository;

import com.example.demo.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    @Query("select t from Trainee t where t.group is null")
    List<Trainee> findUngroupedTrainees();

    @Query("select t from Trainee t where t.group is not null")
    List<Trainee> findGroupedTrainees();
}
