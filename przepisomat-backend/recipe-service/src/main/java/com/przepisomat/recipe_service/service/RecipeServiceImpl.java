package com.przepisomat.recipe_service.service;

import com.przepisomat.recipe_service.dto.ProductDto;
import com.przepisomat.recipe_service.entity.Recipe;
import com.przepisomat.recipe_service.record.RecipeRecord;
import com.przepisomat.recipe_service.repository.RecipeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ChatClient chatClient;

    private final WebClient webClient;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ChatClient.Builder builder, WebClient webClient) {
        this.recipeRepository = recipeRepository;
        this.chatClient = builder
                .defaultSystem("Jesteś ekspertem od gotowania i przepisów kuchennych.")
                .build();
        this.webClient = webClient;
    }

    public Recipe createRecipe(HttpServletRequest request) {

            String authorizationHeader = request.getHeader("Authorization");
            String username = getSubjectFromJwtToken(authorizationHeader);

        List<ProductDto> productDtoList = webClient.get()
                .uri("http://localhost:8081/api/product")
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductDto>>() {})
                .block();


        String message = "Twoim zadaniem jest stworzyć przepis na pyszne danie na podstawie podanych przez użytkownika składników." +
                " Możesz dołożyć swoje produkty. Jeśli jakiś produkt bardzo nie pasuje, to nie musisz go używać." +
                " Zawsze podawaj listę kroków oraz ile i jakich produktów używasz." +
                "Składninki są podane w formie name oraz amount, gdzie amount jest podane w gramach. Nie musisz wykorzystywac całego amount" +
                " Składniki: {ingredients}";

         RecipeRecord recipeRecord = chatClient.prompt()
                .user(userSpec -> userSpec.text(message).param("ingredients", productDtoList.toString()))
                .call()
                .entity(RecipeRecord.class);

         Recipe recipe = convertRecipeRecordToRecipe(recipeRecord);
         recipe.setUsername(username);


         return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getRecipesByUsername(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String username = getSubjectFromJwtToken(authorizationHeader);

        return recipeRepository.getRecipeByUsername(username);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    private Recipe convertRecipeRecordToRecipe(RecipeRecord recipeRecord) {
        return Recipe.builder()
                .title(recipeRecord.title())
                .ingredients(recipeRecord.ingredients())
                .listOfSteps(recipeRecord.listOfSteps())
                .build();
    }

    private String getSubjectFromJwtToken(String authorizationHeader) {

        // Wyciągnij sam token JWT z nagłówka
        String jwtToken = authorizationHeader.substring(7);

        // Odczytaj token i pobierz z niego dane
        Claims claims = Jwts.parser().setSigningKey("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437").parseClaimsJws(jwtToken).getBody();

        // Pobierz identyfikator użytkownika z tokena JWT
        return claims.getSubject();
    }
}
