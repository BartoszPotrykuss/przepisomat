package com.przepisomat.recipe_service.controller;

import com.przepisomat.recipe_service.entity.Recipe;
import com.przepisomat.recipe_service.record.RecipeRecord;
import com.przepisomat.recipe_service.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(HttpServletRequest request) {
        Recipe recipe = recipeService.createRecipe(request);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getRecipesByUsername(HttpServletRequest request) {
        List<Recipe> recipes = recipeService.getRecipesByUsername(request);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
