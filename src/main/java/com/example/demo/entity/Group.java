package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //TODO GTB-知识点: - Group.java:20  @OneToMany和@ManyToOne不一定要成对出现
    @OneToMany
    private List<Trainee> trainee;
    @OneToMany
    private List<Trainer> trainer;
}
