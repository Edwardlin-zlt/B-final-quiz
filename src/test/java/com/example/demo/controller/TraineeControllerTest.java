package com.example.demo.controller;

import static org.mockito.Mockito.*;

import com.example.demo.entity.Group;
import com.example.demo.entity.Trainee;
import com.example.demo.service.TraineeService;
import org.graalvm.compiler.nodes.java.ArrayLengthNode;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TraineeController.class)
@AutoConfigureJsonTesters
class TraineeControllerTest {

    @MockBean
    private TraineeService traineeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<List<Trainee>> traineesJson;

    @Autowired
    private JacksonTester<Trainee> traineeJson;

    private Trainee testTraineeUngrouped;

    @BeforeEach
    public void setup() {
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
            public void should_return_ungrouped_trainees() throws Exception {
                ArrayList<Trainee> trainees = new ArrayList<>();
                trainees.add(testTraineeUngrouped);
                String traineesAsJson = traineesJson.write(trainees).getJson();
                when(traineeService.findUngroupedTrainees()).thenReturn(trainees);

                MockHttpServletResponse response = mockMvc.perform(get("/trainees?grouped=false"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(traineesAsJson);
            }
        }

        @Nested
        class WhenThereIsNoUngroupedTrainees {

            @Test
            public void should_return_empty_list() throws Exception {
                ArrayList<Trainee> trainees = new ArrayList<>();
                String traineesAsJson = traineesJson.write(trainees).getJson();
                when(traineeService.findUngroupedTrainees()).thenReturn(trainees);

                MockHttpServletResponse response = mockMvc.perform(get("/trainees?grouped=false"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(traineesAsJson);
            }
        }
    }

    @Nested
    class CreateNewTrainee {

        @Nested
        class WhenPostValidTraineeData {
            @Test
            public void should_return_created_trainee() throws Exception {
                String TraineeAsJson = traineeJson.write(testTraineeUngrouped).getJson();
                when(traineeService.createNewTrainee(testTraineeUngrouped)).thenReturn(testTraineeUngrouped);

                MockHttpServletRequestBuilder requestBuilder = post("/trainees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TraineeAsJson);
                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(TraineeAsJson);
            }
        }

        @Nested
        class WhenPostInvalidTraineeData {
            @Test
            public void should_response_bad_request() throws Exception {

                MockHttpServletRequestBuilder requestBuilder = post("/trainees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid trainee data");

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest());

                verify(traineeService, never()).createNewTrainee(any(Trainee.class));
            }
        }
    }

}