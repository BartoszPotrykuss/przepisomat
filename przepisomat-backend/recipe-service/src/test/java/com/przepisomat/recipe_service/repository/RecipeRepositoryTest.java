package com.przepisomat.recipe_service.repository;

import com.przepisomat.recipe_service.entity.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void getRecipeByUsername_whenGivenRecipe_ReturnRecipeList() {
        //Arrange
        Recipe recipe1 = Recipe.builder().title("title1").username("user1").build();
        Recipe recipe2 = Recipe.builder().title("title2").username("user2").build();

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        //Act
        List<Recipe> result = recipeRepository.getRecipeByUsername("user1");

        assertEquals(result.size(), 1);
        assertEquals("user1", result.get(0).getUsername());
    }
}
