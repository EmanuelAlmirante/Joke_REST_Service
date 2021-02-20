package com.joke.service;

import com.joke.json.CreateJokeJsonRequest;
import com.joke.json.JokeAllDetailsJsonResponse;
import com.joke.json.JokeJsonResponse;
import com.joke.json.RandomJokeJsonResponse;
import com.joke.json.UpdateJokeJsonRequest;
import com.joke.model.JokeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JokeServiceInterface {
    Page<JokeModel> getAllJokesUsingPages(Pageable pageable);

    List<JokeJsonResponse> getJokesFilteredByTextQuery(String input);

    void createJoke(CreateJokeJsonRequest joke);

    void updateJoke(Long id, UpdateJokeJsonRequest joke);

    void deleteJoke(Long id);

    JokeAllDetailsJsonResponse getAllDetailsJoke(Long id);

    RandomJokeJsonResponse getRandomJoke();
}
