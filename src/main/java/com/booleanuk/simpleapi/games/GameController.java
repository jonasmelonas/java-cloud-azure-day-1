package com.booleanuk.simpleapi.games;

import com.booleanuk.simpleapi.users.User;
import com.booleanuk.simpleapi.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public Game getOneGame(@PathVariable int id) {
        return this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found.")
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Game> getAllGames() {
        return this.gameRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Game createGame(@RequestBody Game game) {
        Game newGame = new Game(game.getTitle(), game.getReleaseYear());
        User userRef = this.userRepository.findById(game.getUser().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        );
        newGame.setUser(userRef);
        return this.gameRepository.save(newGame);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public Game updateGame(@RequestBody Game game, @PathVariable int id) {
        Game gameToUpdate = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found.")
        );
        User userRef = this.userRepository.findById(game.getUser().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        );
        gameToUpdate.setUser(userRef);
        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setReleaseYear(game.getReleaseYear());
        return this.gameRepository.save(gameToUpdate);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Game deleteGame(@PathVariable int id) {
        Game gameToDelete = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found.")
        );
        this.gameRepository.delete(gameToDelete);
        return gameToDelete;
    }
}