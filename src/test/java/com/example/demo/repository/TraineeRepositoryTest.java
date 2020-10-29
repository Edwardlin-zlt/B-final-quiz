//package com.example.demo.repository;
//
//import com.example.demo.entity.Trainee;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DataJpaTest
//class TraineeRepositoryTest {
//    @Autowired
//    private TraineeRepository traineeRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    private Trainee TestTraineeUngrouped;
//
//    @BeforeEach
//    public void setup() {
//        TestTraineeUngrouped = Trainee.builder()
//                .Id(1L)
//                .name("testName")
//                .build();
//        entityManager.persistAndFlush(TestTraineeUngrouped);
//    }
//
//    @Test
//    public void should_return_ungrouped_trainees() {
//        ArrayList<Trainee> trainees = new ArrayList<>();
//        trainees.add(TestTraineeUngrouped);
//        List<Trainee> foundTrainees = traineeRepository.findUngroupedTrainees();
//
//        assertThat(foundTrainees).isEqualTo(trainees);
//    }
//}