package com.przepisomat.recipe_service.service;

import com.przepisomat.recipe_service.dto.ProductDto;
import com.przepisomat.recipe_service.entity.Recipe;
import com.przepisomat.recipe_service.record.RecipeRecord;
import com.przepisomat.recipe_service.repository.RecipeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


//do poprawy
@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ChatClient chatClient;

    @Mock
    private WebClient webClient;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    public void createRecipe_whenCalled_shouldReturnSavedRecipe() {
        // Arrange
        String token = "Bearer testToken";
        String username = "testUser";
        List<ProductDto> productDtoList = List.of(
                new ProductDto("ryż", 200L),
                new ProductDto("kurczak", 500L)
        );

        // Mockowanie WebClient
        WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(recipeService.getSubjectFromJwtToken(anyString())).thenReturn(username);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(eq(HttpHeaders.AUTHORIZATION), eq(token))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class))).thenReturn(Mono.just(productDtoList));

        // Mockowanie ChatClient
        ChatClient.PromptSpec promptSpec = mock(ChatClient.PromptSpec.class);
        when(chatClient.prompt()).thenReturn(promptSpec);
        when(promptSpec.user(any())).thenReturn(promptSpec);
        when(promptSpec.call()).thenReturn(Optional.of(new RecipeRecord("Przepis na ryż z kurczakiem", List.of("krok 1", "krok 2"))));

        // Mockowanie repozytorium
        Recipe expectedRecipe = Recipe.builder().title("Przepis na ryż z kurczakiem").username(username).build();
        when(recipeRepository.save(any(Recipe.class))).thenReturn(expectedRecipe);

        // Act
        Recipe result = recipeService.createRecipe(request);

        // Assert
        assertEquals(expectedRecipe, result);
        verify(recipeRepository).save(any(Recipe.class));
    }
}

