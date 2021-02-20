package com.joke.repository;

import com.joke.model.JokeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeRepository extends JpaRepository<JokeModel, Long> {
    @Query("select j from JokeModel j")
    Page<JokeModel> findAllPage(Pageable pageable);

    @Query("select j from JokeModel j where lower(j.joke) like lower(concat('%', :input, '%'))")
    List<JokeModel> findFullTextSearchByInputTextIgnoreCase(@Param("input") String input);

    @Query(value = "select * from joke order by rand() limit 1 ", nativeQuery = true)
    JokeModel getRandomJoke();
}
