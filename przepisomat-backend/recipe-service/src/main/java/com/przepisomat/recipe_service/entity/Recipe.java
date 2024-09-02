package com.przepisomat.recipe_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String listOfSteps;

    @Column(columnDefinition = "TEXT")
    private String ingredients;
    private String username = "";
}
