package com.joke.json;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JokeAllDetailsJsonResponse {
    @NotNull
    private Long id;
    @NotNull
    private String joke;
    @NotNull
    private Date createdDate;
    @NotNull
    private Date updatedDate;
}
