package com.joke.repository;

import com.joke.model.JokeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JokeRepositoryTests {
    @Autowired
    private JokeRepository jokeRepository;

    @Test
    public void getAllJokesUsingPagesSuccessfully() {
        // Arrange
        JokeModel jokeModel = new JokeModel("This is a joke test.");
        jokeRepository.save(jokeModel);

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<JokeModel> jokeModelPage = jokeRepository.findAllPage(pageable);

        // Assert
        Assertions.assertNotNull(jokeModelPage);
        Assertions.assertNotNull(jokeModelPage.getContent().get(0).getId());
        Assertions.assertEquals(jokeModel.getJoke(), jokeModelPage.getContent().get(0).getJoke());
        Assertions.assertNotNull(jokeModelPage.getContent().get(0).getCreatedDate());
        Assertions.assertNotNull(jokeModelPage.getContent().get(0).getUpdatedDate());
    }

    @Test
    public void getJokesFilteredByTextQuerySuccessfully() {
        // Arrange
        JokeModel firstJokeModel = new JokeModel("This is a first joke test.");
        jokeRepository.save(firstJokeModel);
        JokeModel secondJokeModel = new JokeModel("This is a second joke test.");
        jokeRepository.save(secondJokeModel);

        String input = "first";

        // Act
        List<JokeModel> jokeModelList = jokeRepository.findFullTextSearchByInputTextIgnoreCase(input);

        // Assert
        Assertions.assertNotNull(jokeModelList);
        Assertions.assertEquals(1, jokeModelList.size());
        Assertions.assertEquals(firstJokeModel.getJoke(), jokeModelList.get(0).getJoke());
    }

    @Test
    public void createJokeSuccessfully() {
        // Arrange
        JokeModel jokeModel = new JokeModel("This is a joke test.");

        // Act
        jokeRepository.save(jokeModel);

        JokeModel jokeModelFetched = jokeRepository.findAll().get(0);

        // Assert
        Assertions.assertNotNull(jokeModelFetched);
        Assertions.assertEquals(jokeModel.getJoke(), jokeModelFetched.getJoke());
    }

    @Test
    public void updateJokeSuccessfully() {
        // Arrange
        JokeModel jokeModel = new JokeModel("This is a joke test.");
        jokeRepository.save(jokeModel);

        Date updateDateTime = jokeModel.getUpdatedDate();

        Optional<JokeModel> jokeModelFetched = jokeRepository.findById(jokeModel.getId());
        jokeModelFetched.get().setJoke("This is a joke update test");

        // Act
        jokeRepository.save(jokeModelFetched.get());

        JokeModel jokeModelUpdated = jokeRepository.findAll().get(0);

        // Assert
        Assertions.assertNotNull(jokeModelUpdated);
        Assertions.assertEquals(jokeModel.getId(), jokeModelUpdated.getId());
        Assertions.assertEquals("This is a joke update test", jokeModelUpdated.getJoke());
        Assertions.assertEquals(jokeModel.getCreatedDate(), jokeModelUpdated.getCreatedDate());
        Assertions.assertNotEquals(updateDateTime, jokeModelUpdated.getUpdatedDate());
    }

    @Test
    public void deleteJokeSuccessfully() {
        // Arrange
        JokeModel jokeModel = new JokeModel("This is a joke test.");
        jokeRepository.save(jokeModel);

        // Act
        jokeRepository.deleteById(jokeModel.getId());

        Optional<JokeModel> jokeModelFetched = jokeRepository.findById(jokeModel.getId());

        // Assert
        Assertions.assertTrue(jokeModelFetched.isEmpty());
    }

    @Test
    public void getAllDetailsJokeSuccessfully() {
        // Arrange
        JokeModel jokeModel = new JokeModel("This is a joke test.");
        jokeRepository.save(jokeModel);

        // Act
        Optional<JokeModel> optionalJokeModel = jokeRepository.findById(jokeModel.getId());

        JokeModel jokeModelFetched = optionalJokeModel.get();

        // Assert
        Assertions.assertNotNull(jokeModelFetched);
        Assertions.assertEquals(jokeModel.getId(), jokeModelFetched.getId());
        Assertions.assertEquals(jokeModel.getJoke(), jokeModelFetched.getJoke());
        Assertions.assertEquals(jokeModel.getCreatedDate(), jokeModelFetched.getCreatedDate());
        Assertions.assertEquals(jokeModel.getUpdatedDate(), jokeModelFetched.getUpdatedDate());
    }

    @Test
    public void getRandomJokeSuccessfully() {
        // Arrange
        JokeModel firstJokeModel = new JokeModel("This is a first joke test.");
        jokeRepository.save(firstJokeModel);
        JokeModel secondJokeModel = new JokeModel("This is a second joke test.");
        jokeRepository.save(secondJokeModel);

        // Act
        JokeModel jokeModelFetched = jokeRepository.getRandomJoke();

        // Assert
        Assertions.assertNotNull(jokeModelFetched);
        Assertions.assertTrue(jokeModelFetched.getId().equals(firstJokeModel.getId()) ||
                              jokeModelFetched.getId().equals(secondJokeModel.getId()));
        Assertions.assertTrue(jokeModelFetched.getJoke().equals(firstJokeModel.getJoke()) ||
                              jokeModelFetched.getJoke().equals(secondJokeModel.getJoke()));
        Assertions.assertTrue(jokeModelFetched.getCreatedDate().equals(firstJokeModel.getCreatedDate()) ||
                              jokeModelFetched.getCreatedDate().equals(secondJokeModel.getCreatedDate()));
        Assertions.assertTrue(jokeModelFetched.getUpdatedDate().equals(firstJokeModel.getUpdatedDate()) ||
                              jokeModelFetched.getUpdatedDate().equals(secondJokeModel.getUpdatedDate()));
    }
}
