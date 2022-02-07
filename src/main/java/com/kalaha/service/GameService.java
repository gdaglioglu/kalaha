package com.kalaha.service;

import com.kalaha.model.GameConfig;
import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import com.kalaha.rule.GameRule;
import com.kalaha.rule.input.IsGameOngoingRule;
import com.kalaha.rule.input.IsPlayersTurnRule;
import com.kalaha.rule.input.IsValidPitRule;
import com.kalaha.rule.play.CollectStonesFromOppositePitRule;
import com.kalaha.rule.play.DeterminePlayerTurnRule;
import com.kalaha.rule.play.DistributeStonesRule;
import com.kalaha.rule.win.AreAllPitsEmptyForEitherPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The service that manages the creation and play of the game.
 */
@Service
public class GameService {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(GameService.class);

    /**
     * The reference to the game.
     */
    private KalahaGame game;

    /**
     * Creates a new kalaha game.
     *
     * @return the data of the newly created game.
     */
    public GameData newGame(GameConfig gameConfig) {

        List<GameRule> rules = new ArrayList<>();
        rules.add(new IsGameOngoingRule());
        rules.add(new IsPlayersTurnRule());
        rules.add(new IsValidPitRule());

        rules.add(new DistributeStonesRule());
        rules.add(new CollectStonesFromOppositePitRule());
        rules.add(new DeterminePlayerTurnRule());

        rules.add(new AreAllPitsEmptyForEitherPlayer());

        gameConfig.setRules(rules);

        game = new KalahaGame(gameConfig);
        GameData gameData = game.getGameData();
        logger.debug("Game retrieved: {}", gameData);

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
        logger.debug("Turn played: {}", gameData);
        return gameData;
    }

    /**
     * Retrieves the game data.
     *
     * @return the data that represents the game.
     */
    public GameData getGame() {

        if (this.game == null) {
            logger.error("Game not initialised");
            return null;
        }

        GameData gameData = game.getGameData();
        logger.debug("Game retrieved: {}", gameData);
        return gameData;
    }
}