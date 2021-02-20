package com.joke.json;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RandomJokeJsonResponse {
    @NotNull
    private String joke;
}
