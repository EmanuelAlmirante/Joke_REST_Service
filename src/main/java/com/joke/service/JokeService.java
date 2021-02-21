package com.joke.service;

import com.joke.exception.BusinessException;
import com.joke.json.CreateJokeJsonRequest;
import com.joke.json.JokeAllDetailsJsonResponse;
import com.joke.json.JokeJsonResponse;
import com.joke.json.RandomJokeJsonResponse;
import com.joke.json.UpdateJokeJsonRequest;
import com.joke.model.JokeModel;
import com.joke.repository.JokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JokeService implements JokeServiceInterface {
    private final JokeRepository jokeRepository;

    @Autowired
    public JokeService(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public Page<JokeModel> getAllJokesUsingPages(Pageable pageable) {
        return jokeRepository.findAllPage(pageable);
    }

    @Override
    public List<JokeJsonResponse> getJokesFilteredByTextQuery(String input) {
        List<JokeModel> jokeModelList = jokeRepository.findFullTextSearchByInputTextIgnoreCase(input);

        List<JokeJsonResponse> jokeJsonResponseList = new ArrayList<>();

        for (JokeModel jokeModel : jokeModelList) {
            String joke = jokeModel.getJoke();
            JokeJsonResponse jokeJsonResponse = new JokeJsonResponse(joke);

            jokeJsonResponseList.add(jokeJsonResponse);
        }

        return jokeJsonResponseList;
    }

    @Override
    public void createJoke(CreateJokeJsonRequest joke) {
        JokeModel jokeModel = new JokeModel(joke.getJoke());

        jokeRepository.save(jokeModel);
    }

    @Override
    public void updateJoke(Long id, UpdateJokeJsonRequest joke) {
        Optional<JokeModel> jokeModel = jokeRepository.findById(id);

        if (jokeModel.isPresent()) {
            jokeModel.get().setJoke(joke.getJoke());

            jokeRepository.save(jokeModel.get());
        } else {
            throw new BusinessException("The joke with id " + id + " does not exist");
        }

    }

    @Override
    public void deleteJoke(Long id) {
        jokeRepository.deleteById(id);
    }

    @Override
    public JokeAllDetailsJsonResponse getAllDetailsJoke(Long id) {
        Optional<JokeModel> optionalJokeModel = jokeRepository.findById(id);
        JokeAllDetailsJsonResponse jokeAllDetailsJsonResponse;

        if (optionalJokeModel.isPresent()) {
            JokeModel jokeModel = optionalJokeModel.get();
            Long jokeId = jokeModel.getId();
            String joke = jokeModel.getJoke();
            Date createdDate = jokeModel.getCreatedDate();
            Date updatedDate = jokeModel.getUpdatedDate();

            jokeAllDetailsJsonResponse = new JokeAllDetailsJsonResponse(jokeId, joke, createdDate, updatedDate);
        } else {
            throw new BusinessException("The joke with id " + id + " does not exist");
        }

        return jokeAllDetailsJsonResponse;
    }

    @Override
    public RandomJokeJsonResponse getRandomJoke() {
        JokeModel jokeModel = jokeRepository.getRandomJoke();

        return new RandomJokeJsonResponse(jokeModel.getJoke());
    }
}
