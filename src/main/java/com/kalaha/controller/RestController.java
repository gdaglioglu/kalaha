package com.kalaha.controller;

import com.kalaha.model.GameConfig;
import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import com.kalaha.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * Rest controller that exposes APIs to control game logic.
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/game")
public class RestController {

    /**
     * The game endpoint
     */
    @Value("${server.kalaha.endpoint}")
    private String endPoint;

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
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void newGame(@RequestBody GameConfig gameConfig, HttpServletResponse response) {

        GameData gameData = gameService.newGame(gameConfig);

        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(endPoint + "/" + gameData.getId()).toUriString());
    }

    /**
     * Retrieves the game data<p/>
     *
     * @return the representation of the game data.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameData getGame(@PathVariable long id) {

        log.debug("Game requested: id = {}", id);

        GameData gameData = gameService.getGame(id);

        if (gameData == null) {
            log.error("Game not initialized!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game is not initialized, please create a game first");
        }
        log.debug("Game retrieved: {}", gameData);

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
        log.debug("Game retrieved: {}", gameData);
        return gameData;
    }
}