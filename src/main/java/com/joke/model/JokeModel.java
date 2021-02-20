package com.joke.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "joke")
public class JokeModel {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String joke;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date updatedDate;

    public JokeModel(String joke) {
        this.joke = joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}
