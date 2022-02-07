package com.kalaha.controller;

import com.kalaha.model.GameConfig;
import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import com.kalaha.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rest controller that exposes APIs to control game logic.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/game")
public class RestController {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(RestController.class);

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
    public RestController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new game.
     *
     * @param gameConfig the config to be used in game creation.
     * @return the representation of the game data.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GameData newGame(@RequestBody GameConfig gameConfig) {

        GameData gameData = gameService.newGame(gameConfig);
        logger.debug("Game created: {}", gameData);
        return gameData;
    }

    /**
     * TODO: Review GameData to see whether we send all relevant data.
     * Retrieves the game data<p/>
     *
     * @return the representation of the game data.
     */
    @GetMapping()
    public GameData getGame() {

        GameData gameData = gameService.getGame();

        if (gameData == null) {
            logger.error("Game not initialized!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game is not initialized, please create a game first");
        }
        logger.debug("Game retrieved: {}", gameData);

        return gameData;
    }

    /**
     * Initiates a move by either of the players.
     *
     * @return the representation of the game data.
     */
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public GameData play(@RequestBody PlayData input) {

        GameData gameData = gameService.play(input);
        logger.debug("Game retrieved: {}", gameData);
        return gameData;
    }
}