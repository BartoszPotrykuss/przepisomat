package com.przepisomat.recipe_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.przepisomat.recipe_service.entity.Recipe;
import com.przepisomat.recipe_service.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void createRecipe_whenCalled_shouldReturnCreatedRecipe() throws Exception {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Przepis na ryż z kurczakiem");

        when(recipeService.createRecipe(any(HttpServletRequest.class))).thenReturn(recipe);

        // Act & Assert
        mockMvc.perform(post("/api/recipe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Przepis na ryż z kurczakiem"));
    }

    @Test
    public void getRecipesByUsername_whenCalled_shouldReturnListOfRecipes() throws Exception {
        // Arrange
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setTitle("Przepis na ryż z kurczakiem");

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setTitle("Przepis na makaron");

        List<Recipe> recipeList = List.of(recipe1, recipe2);

        when(recipeService.getRecipesByUsername(any(HttpServletRequest.class))).thenReturn(recipeList);

        // Act & Assert
        mockMvc.perform(get("/api/recipe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Przepis na ryż z kurczakiem"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Przepis na makaron"));
    }

    @Test
    public void deleteRecipeById_whenCalled_shouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/recipe/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify that the service method was called with the correct ID
        verify(recipeService).deleteRecipeById(1L);
    }
}

