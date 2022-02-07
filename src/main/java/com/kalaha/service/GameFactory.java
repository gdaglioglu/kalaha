package com.kalaha.service;

import com.kalaha.model.GameConfig;
import org.springframework.stereotype.Component;

/**
 * Class responsible for creating games.
 */
@Component
public class GameFactory {

    /**
     * Creates and return a game. <p/>
     * This can be reused to create other types of games if necessary.
     * @param gameConfig the configuration to be used in game creation.
     * @return the newly created game.
     */
    public Game createGame(GameConfig gameConfig) {
        return new KalahaGame(gameConfig);
    }
}
