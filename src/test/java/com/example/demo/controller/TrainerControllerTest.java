package com.example.demo.controller;

import com.example.demo.entity.Trainer;
import com.example.demo.exception.TrainerNotExistException;
import com.example.demo.service.TrainerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainerController.class)
@AutoConfigureJsonTesters
class TrainerControllerTest {
    @MockBean
    private TrainerService trainerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<List<Trainer>> trainersJson;

    @Autowired
    private JacksonTester<Trainer> trainerJson;

    private Trainer testTrainerUngrouped;

    @BeforeEach
    public void setup() {
        testTrainerUngrouped = Trainer.builder()
                .Id(1L)
                .name("testTrainer-ungrouped")
                .build();
    }

    @AfterEach
    public void clear() {
        reset(trainerService);
    }

    @Nested
    class FindUngroupedTrainers {

        @Nested
        class WhenThereIsUngroupedTrainers {

            @Test
            public void should_return_ungrouped_trainers() throws Exception {
                ArrayList<Trainer> trainers = new ArrayList<>();
                trainers.add(testTrainerUngrouped);
                String trainersAsJson = trainersJson.write(trainers).getJson();
                when(trainerService.findUngroupedTrainers()).thenReturn(trainers);

                MockHttpServletResponse response = mockMvc.perform(get("/trainers?grouped=false"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(trainersAsJson);
            }
        }

        @Nested
        class WhenThereIsNoUngroupedTrainers {

            @Test
            public void should_return_empty_list() throws Exception {
                ArrayList<Trainer> trainers = new ArrayList<>();
                String trainersAsJson = trainersJson.write(trainers).getJson();
                when(trainerService.findUngroupedTrainers()).thenReturn(trainers);

                MockHttpServletResponse response = mockMvc.perform(get("/trainers?grouped=false"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(trainersAsJson);
            }
        }
    }

    @Nested
    class CreateNewTrainer {

        @Nested
        class WhenPostValidTrainerData {
            @Test
            public void should_return_created_trainer() throws Exception {
                String TrainerAsJson = trainerJson.write(testTrainerUngrouped).getJson();
                when(trainerService.createNewTrainer(testTrainerUngrouped)).thenReturn(testTrainerUngrouped);

                MockHttpServletRequestBuilder requestBuilder = post("/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TrainerAsJson);
                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(TrainerAsJson);
            }
        }

        @Nested
        class WhenPostInvalidTrainerData {
            @Test
            public void should_response_bad_request() throws Exception {

                MockHttpServletRequestBuilder requestBuilder = post("/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid trainer data");

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest());

                verify(trainerService, never()).createNewTrainer(any(Trainer.class));
            }
        }
    }

    @Nested
    class deleteTrainee {

        @Nested
        class WhenTrainerByIdExist {

            @Test
            public void should_delete_a_trainer() throws Exception {
                doNothing().when(trainerService).deleteTrainerById(1L);

                mockMvc.perform(delete("/trainers/{trainer_id}", 1))
                        .andExpect(status().isNoContent());

                verify(trainerService, times(1)).deleteTrainerById(1L);
            }
        }

        @Nested
        class WhenTrainerByIdNotExist {

            @Test
            public void should_response_404() throws Exception {
                doThrow(new TrainerNotExistException("Trainer didn't Exist")).when(trainerService).deleteTrainerById(1L);

                mockMvc.perform(delete("/trainers/{trainer_id}", 1))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.message", containsString("Trainer didn't Exist")));

                verify(trainerService, times(1)).deleteTrainerById(1L);
            }
        }
    }
}
