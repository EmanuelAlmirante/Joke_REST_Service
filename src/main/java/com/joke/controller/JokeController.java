package com.joke.controller;

import com.joke.json.CreateJokeJsonRequest;
import com.joke.json.JokeAllDetailsJsonResponse;
import com.joke.json.JokeJsonResponse;
import com.joke.json.RandomJokeJsonResponse;
import com.joke.json.UpdateJokeJsonRequest;
import com.joke.model.JokeModel;
import com.joke.service.JokeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/joke")
public class JokeController {
    private final JokeServiceInterface jokeService;

    @Autowired
    public JokeController(JokeServiceInterface jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping("/jokes")
    @ResponseStatus(HttpStatus.OK)
    public Page<JokeModel> getAllJokesUsingPages(Pageable pageable) {
        return jokeService.getAllJokesUsingPages(pageable);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<JokeJsonResponse> getJokesFilteredByTextQuery(@RequestParam(value = "text") String input) {
        return jokeService.getJokesFilteredByTextQuery(input);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createJoke(@Valid @RequestBody CreateJokeJsonRequest joke) {
        jokeService.createJoke(joke);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateJoke(@PathVariable Long id, @RequestBody UpdateJokeJsonRequest joke) {
        jokeService.updateJoke(id, joke);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteJoke(@PathVariable Long id) {
        jokeService.deleteJoke(id);
    }

    @GetMapping("/details/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JokeAllDetailsJsonResponse getAllDetailsJoke(@PathVariable Long id) {
        return jokeService.getAllDetailsJoke(id);
    }

    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    public RandomJokeJsonResponse getRandomJoke() {
        return jokeService.getRandomJoke();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<JokeModel> getAllJokes() {
        return jokeService.getAllJokes();
    }
}
