package com.kalaha.service;

import com.kalaha.model.GameConfig;
import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import com.kalaha.rule.GameRule;
import com.kalaha.rule.input.IsGameOngoing;
import com.kalaha.rule.input.IsPlayersTurn;
import com.kalaha.rule.input.IsValidPit;
import com.kalaha.rule.play.CollectStonesFromOppositePitRule;
import com.kalaha.rule.play.DeterminePlayerTurnRule;
import com.kalaha.rule.play.DistributeStonesRule;
import com.kalaha.rule.win.AreAllPitsEmptyForEitherPlayer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public GameData newGame(GameConfig gameConfig) {

        List<GameRule> rules = new ArrayList<>();
        rules.add(new IsGameOngoing());
        rules.add(new IsPlayersTurn());
        rules.add(new IsValidPit());

        rules.add(new DistributeStonesRule());
        rules.add(new CollectStonesFromOppositePitRule());
        rules.add(new DeterminePlayerTurnRule());

        rules.add(new AreAllPitsEmptyForEitherPlayer());

        gameConfig.setRules(rules);

        game = new KalahaGame(gameConfig);
        return game.newGame();
    }

    /**
     * Plays a turn for either of the players.
     * @param playData the player's selection.
     * @return the updated game data after turn is completed.
     */
    public GameData play(PlayData playData) {

        return game.playTurn(playData);
    }

    /**
     * TODO: Don't return if game is not created yet.
     * Retrieves the game data.
     * @return the data that represents the game.
     */
    public GameData getGame() {
        if (this.game != null) {
            return game.getGameData();
        }
        //return newGame(new GameConfig("Player 1", "Player 2", 6,6));
        return null;
    }
}