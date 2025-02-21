package com.booleanuk.simpleapi.games;

import com.booleanuk.simpleapi.users.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private int releaseYear;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    @JsonIgnoreProperties("games")
    private User user;

    public Game(String title, int releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }
}
