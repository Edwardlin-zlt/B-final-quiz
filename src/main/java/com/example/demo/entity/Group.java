package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    private Long id;
    @OneToMany
    private Trainee trainee;
}
