package com.kalaha.controller;

import com.kalaha.model.GameConfig;
import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import com.kalaha.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * TODO: HATEOS metadata.
 * Rest controller that exposes APIs to control game logic.
 */
@RestController
public class GameRestController {

    /**
     * Service to manipulate game logic.
     */
    private final GameService gameService;

    /**
     * Constructs a rest controller.
     *
     * @param gameService The reference to service.
     */
    @Autowired
    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * TODO: Review GameData to see whether we send all relevant data
     * Creates a new game.<p/>
     *       Do we really need to expose this API?
     * Maps a POST to /game endpoint.
     *
     * @return The representation of the game data.
     */
    @PostMapping("/game")
    public GameData newGame(@RequestBody GameConfig gameConfig) {

        return gameService.newGame(gameConfig);
    }

    /**
     * TODO: Review GameData to see whether we send all relevant data.
     * Creates a new game.<p/>
     * Maps a GET to /game endpoint.
     *
     * @return The representation of the game data.
     */
    @GetMapping("/game")
    public GameData getGame() {
        GameData game = gameService.getGame();

        if(game == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game is not initialized, please go to /createGame page");
        }

        return game;
    }

    /**
     * TODO: Replace GameData with a POJO to break dependency between client and server.
     * Initiates a move by either of the players.<p/>
     * Maps a PUT to /game endpoint.
     *
     * @return The representation of the game data.
     */
    @PutMapping(value = "/game")
    public GameData play(@RequestBody PlayData input) {

        return gameService.play(input);
    }
}