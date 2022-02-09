package com.kalaha.service;

import com.kalaha.model.GameConfig;
import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service that manages the creation and play of the game.
 */
@Slf4j
@Service
public class GameService {

    /**
     * The reference to the game.
     */
    private Game game;

    /**
     * Factory class to used to create games.
     */
    private final GameFactory gameFactory;

    /**
     * Constructor for this service.
     *
     * @param gameFactory the factory to create games.
     */
    @Autowired
    public GameService(GameFactory gameFactory) {

        this.gameFactory = gameFactory;
    }

    /**
     * Creates a new kalaha game.
     *
     * @return the data of the newly created game.
     */
    public GameData newGame(GameConfig gameConfig) {

        game = gameFactory.createGame(gameConfig);
        GameData gameData = game.getGameData();
        log.debug("Game retrieved: {}", gameData);

        return gameData;
    }

    /**
     * Plays a turn for either of the players.
     *
     * @param playData the player's selection.
     * @return the updated game data after turn is completed.
     */
    public GameData play(PlayData playData) {

        GameData gameData = game.playTurn(playData);
        log.debug("Turn played: {}", gameData);
        return gameData;
    }

    /**
     * Retrieves the game data. Note that id is ignored at the moment.
     *
     * @param id identifier of the game.
     * @return the data that represents the game.
     */
    public GameData getGame(Long id) {

        log.debug("Game requested: id = {}", id);

        if (this.game == null) {
            log.error("Game not initialised");
            return null;
        }

        GameData gameData = game.getGameData();
        log.debug("Game retrieved: {}", gameData);
        return gameData;
    }
}