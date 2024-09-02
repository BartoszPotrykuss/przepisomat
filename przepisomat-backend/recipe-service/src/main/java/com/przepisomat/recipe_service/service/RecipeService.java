package com.przepisomat.recipe_service.service;

import com.przepisomat.recipe_service.entity.Recipe;
import com.przepisomat.recipe_service.record.RecipeRecord;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RecipeService {

    public Recipe createRecipe(HttpServletRequest request);

    List<Recipe> getRecipesByUsername(HttpServletRequest request);

    void deleteRecipeById(Long id);
}
