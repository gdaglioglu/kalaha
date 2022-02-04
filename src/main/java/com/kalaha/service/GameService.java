package com.kalaha.service;

import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import org.springframework.stereotype.Service;

/**
 * The service that manages the creation and play of the game.
 */
@Service
public class GameService {

    /**
     * The reference to the kalaha game.
     */
    private KalahaGame game;

    /**
     * Creates a new kalaha game.
     *
     * @return the data of the newly created game.
     */
    public GameData newGame() {

        game = new KalahaGame(6, 6);
        return game.newGame();
    }

    /**
     * Plays a turn for either of the players.
     * @param input the player's selection.
     * @return the updated game data after turn is completed.
     */
    public GameData play(PlayData input) {

        return game.play(input);
    }

    /**
     * Retrieves the game data.
     * @return the data that represents the game.
     */
    public GameData getGame() {
        if (this.game != null) {
            return game.getGameData();
        }
        return newGame();
    }
}