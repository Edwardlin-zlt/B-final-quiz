package com.example.demo.service;

import com.example.demo.entity.Trainee;
import com.example.demo.exception.TraineeNotExistException;
import com.example.demo.repository.TraineeRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    private TraineeService traineeService;

    @Mock
    private TraineeRepository traineeRepository;

    private Trainee testTrainee;

    @BeforeEach
    public void setupTest() {
        traineeService = new TraineeService(traineeRepository);
        testTrainee = Trainee.builder()
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

    @Nested
    class CreateNewTrainee {

        @Test
        public void should_return_saved_trainee() {
            Trainee expectTrainee = Trainee.builder()
                    .Id(1L)
                    .name(testTrainee.getName())
                    .build();
            when(traineeRepository.save(testTrainee))
                    .thenReturn(expectTrainee);

            Trainee newTrainee = traineeService.createNewTrainee(testTrainee);

            assertThat(newTrainee).isEqualTo(expectTrainee);
        }
    }

    @Nested
    class DeleteTrainee {

        @Nested
        class WhenExist {
            @Test
            public void should_delete_trainee() throws TraineeNotExistException {
                when(traineeRepository.findById(1L)).thenReturn(Optional.of(testTrainee));
                doNothing().when(traineeRepository).deleteById(1L);

                traineeService.deleteTraineeById(1L);

                verify(traineeRepository, times(1)).deleteById(1L);
            }
        }

        @Nested
        class WhenNotExist {
            @Test
            public void should_Throw_TraineeNotExistException() {
                when(traineeRepository.findById(1L)).thenReturn(Optional.empty());


                TraineeNotExistException traineeNotExistException = assertThrows(TraineeNotExistException.class, () -> {
                    traineeService.deleteTraineeById(1L);
                });

                AssertionsForClassTypes.assertThat(traineeNotExistException.getMessage()).isEqualTo("Trainee didn't Exist");
                verify(traineeRepository, never()).deleteById(1L);
            }
        }

    }


}