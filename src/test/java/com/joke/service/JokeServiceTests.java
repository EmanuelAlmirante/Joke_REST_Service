package com.joke.service;

import com.joke.exception.BusinessException;
import com.joke.json.CreateJokeJsonRequest;
import com.joke.json.JokeAllDetailsJsonResponse;
import com.joke.json.JokeJsonResponse;
import com.joke.json.RandomJokeJsonResponse;
import com.joke.json.UpdateJokeJsonRequest;
import com.joke.model.JokeModel;
import com.joke.repository.JokeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JokeServiceTests {
    @Mock
    private JokeRepository jokeRepository;
    @InjectMocks
    private JokeService jokeService;
    @Mock
    Page<JokeModel> jokeModelPage;

    @Test
    public void getAllJokesUsingPagesSuccessfully() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        when(jokeRepository.findAllPage(pageable)).thenReturn(jokeModelPage);

        // Act
        Page<JokeModel> jokeModelPage = jokeService.getAllJokesUsingPages(pageable);

        // Assert
        Assertions.assertNotNull(jokeModelPage);
    }

    @Test
    public void getJokesFilteredByTextQuerySuccessfully() {
        // Arrange
        String input = "first";
        JokeModel jokeModelFirst = new JokeModel("This is a first joke filter by text query test.");
        List<JokeModel> jokeModelList = Collections.singletonList(jokeModelFirst);

        when(jokeRepository.findFullTextSearchByInputTextIgnoreCase(input)).thenReturn(jokeModelList);

        // Act
        List<JokeJsonResponse> jokeJsonResponseList = jokeService.getJokesFilteredByTextQuery(input);

        // Assert
        Assertions.assertNotNull(jokeJsonResponseList);
        Assertions.assertEquals(jokeModelFirst.getJoke(), jokeJsonResponseList.get(0).getJoke());
    }


    @Test
    public void createJokeSuccessfully() {
        // Arrange
        CreateJokeJsonRequest createJokeJsonRequest = new CreateJokeJsonRequest("This is a joke create test.");

        // Act
        jokeService.createJoke(createJokeJsonRequest);

        // Assert
        verify(jokeRepository, times(1)).save(any(JokeModel.class));
    }

    @Test
    public void updateJokeSuccessfully() {
        // Arrange
        Long id = 1L;
        JokeModel jokeModel = new JokeModel("This is a joke test.");
        UpdateJokeJsonRequest updateJokeJsonRequest = new UpdateJokeJsonRequest("This is a joke update test.");

        when(jokeRepository.findById(anyLong())).thenReturn(Optional.of(jokeModel));

        // Act
        jokeService.updateJoke(id, updateJokeJsonRequest);

        // Assert
        verify(jokeRepository, times(1)).save(jokeModel);
    }

    @Test
    public void updateJokeFails() {
        // Arrange
        Long id = 1L;
        UpdateJokeJsonRequest updateJokeJsonRequest = new UpdateJokeJsonRequest();

        when(jokeRepository.findById(anyLong()))
                .thenThrow(new BusinessException("The joke with id " + id + " does not exist"));

        // Act && Assert
        Assertions.assertThrows(BusinessException.class, () -> jokeService.updateJoke(2L, updateJokeJsonRequest));
    }

    @Test
    public void deleteJokeSuccessfully() {
        // Arrange
        Long id = 1L;

        // Act
        jokeService.deleteJoke(id);

        // Assert
        verify(jokeRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllDetailsJokeSuccessfully() {
        // Arrange
        JokeModel jokeModel = new JokeModel("This is a joke test.");
        JokeAllDetailsJsonResponse jokeAllDetailsJsonResponse;

        when(jokeRepository.findById(anyLong())).thenReturn(Optional.of(jokeModel));

        // Act
        jokeAllDetailsJsonResponse = jokeService.getAllDetailsJoke(1L);

        // Assert
        Assertions.assertEquals(jokeModel.getJoke(), jokeAllDetailsJsonResponse.getJoke());
    }

    @Test
    public void getAllDetailsJokeFails() {
        // Arrange
        Long id = 1L;

        when(jokeRepository.findById(anyLong()))
                .thenThrow(new BusinessException("The joke with id " + id + " does not exist"));

        // Act && Assert
        Assertions.assertThrows(BusinessException.class, () -> jokeService.getAllDetailsJoke(2L));
    }

    @Test
    public void getRandomJokeSuccessfully() {
        // Arrange
        JokeModel jokeModel = new JokeModel("This is a joke test.");
        RandomJokeJsonResponse jokeJsonResponse;

        when(jokeRepository.getRandomJoke()).thenReturn(jokeModel);

        // Act
        jokeJsonResponse = jokeService.getRandomJoke();

        // Assert
        Assertions.assertEquals(jokeModel.getJoke(), jokeJsonResponse.getJoke());
    }
}
