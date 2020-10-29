package com.example.demo.service;


import com.example.demo.entity.Trainer;
import com.example.demo.exception.TrainerNotExistException;
import com.example.demo.repository.TrainerRepository;
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
public class TrainerServiceTest {

    private TrainerService trainerService;

    @Mock
    private TrainerRepository trainerRepository;

    private Trainer testTrainerUngrouped;

    @BeforeEach
    public void setupTest() {
        trainerService = new TrainerService(trainerRepository);
        testTrainerUngrouped = Trainer.builder()
                .Id(1L)
                .name("testUser-grouped")
                .build();
    }

    @Nested
    class FindUngroupedTrainers {

        @Nested
        class WhenThereIsUngroupedTrainers {

            @Test
            public void should_return_a_list_of_Trainers() {
                ArrayList<Trainer> trainers = new ArrayList<>();
                when(trainerRepository.findUngroupedTrainers()).thenReturn(trainers);

                List<Trainer> ungroupedTrainers = trainerService.findUngroupedTrainers();

                assertThat(ungroupedTrainers).isEqualTo(trainers);
            }
        }

        @Nested
        class WhenThereIsNoUngroupedTrainers {

            @Test
            public void should_return_empty_list() {
                ArrayList<Trainer> trainers = new ArrayList<>();
                when(trainerRepository.findUngroupedTrainers()).thenReturn(trainers);

                List<Trainer> ungroupedTrainers = trainerService.findUngroupedTrainers();

                assertThat(ungroupedTrainers).isEqualTo(trainers);
            }
        }
    }


    @Nested
    class CreateNewTrainer {

        @Test
        public void should_return_saved_trainer() {
            Trainer expectTrainer = Trainer.builder()
                    .Id(1L)
                    .name(testTrainerUngrouped.getName())
                    .build();
            when(trainerRepository.save(testTrainerUngrouped))
                    .thenReturn(expectTrainer);

            Trainer newTrainer = trainerService.createNewTrainer(testTrainerUngrouped);

            assertThat(newTrainer).isEqualTo(expectTrainer);
        }
    }

    @Nested
    class DeleteTrainer {

        @Nested
        class WhenExist {
            @Test
            public void should_delete_trainer() throws TrainerNotExistException {
                when(trainerRepository.findById(1L)).thenReturn(Optional.of(testTrainerUngrouped));
                doNothing().when(trainerRepository).deleteById(1L);

                trainerService.deleteTrainerById(1L);

                verify(trainerRepository, times(1)).deleteById(1L);
            }
        }

        @Nested
        class WhenNotExist {
            @Test
            public void should_Throw_TrainerNotExistException() {
                when(trainerRepository.findById(1L)).thenReturn(Optional.empty());


                TrainerNotExistException trainerNotExistException = assertThrows(TrainerNotExistException.class, () -> {
                    trainerService.deleteTrainerById(1L);
                });

                AssertionsForClassTypes.assertThat(trainerNotExistException.getMessage()).isEqualTo("Trainer didn't Exist");
                verify(trainerRepository, never()).deleteById(1L);
            }
        }

    }

}
