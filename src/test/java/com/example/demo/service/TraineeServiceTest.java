package com.example.demo.service;

import com.example.demo.entity.Trainee;
import com.example.demo.repository.TraineeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    private TraineeService traineeService;

    @Mock
    private TraineeRepository traineeRepository;

    private Trainee testTraineeUngrouped;

    @BeforeEach
    public void setupTest() {
        traineeService = new TraineeService(traineeRepository);
        testTraineeUngrouped = Trainee.builder()
                .Id(1L)
                .name("testUser-grouped")
                .build();
    }

    @Nested
    class FindUngroupedTrainees {

        @Nested
        class WhenThereIsUngroupedTrainees {

            @Test
            public void should_return_a_list_of_Trainees() {
                ArrayList<Trainee> trainees = new ArrayList<>();
                when(traineeRepository.findUngroupedTrainees()).thenReturn(trainees);

                List<Trainee> ungroupedTrainees = traineeService.findUngroupedTrainees();

                assertThat(ungroupedTrainees).isEqualTo(trainees);
            }
        }

        @Nested
        class WhenThereIsNoUngroupedTrainees {

            @Test
            public void should_return_empty_list() {
                ArrayList<Trainee> trainees = new ArrayList<>();
                when(traineeRepository.findUngroupedTrainees()).thenReturn(trainees);

                List<Trainee> ungroupedTrainees = traineeService.findUngroupedTrainees();

                assertThat(ungroupedTrainees).isEqualTo(trainees);
            }

        }
    }

}