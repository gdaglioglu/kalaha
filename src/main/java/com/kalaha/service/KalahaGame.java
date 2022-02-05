package com.kalaha.service;

import com.kalaha.model.*;
import com.kalaha.rule.GameRule;
import com.kalaha.rule.input.InputRule;
import com.kalaha.rule.input.IsGameOngoing;
import com.kalaha.rule.play.PlayRule;
import com.kalaha.rule.win.WinRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Game rules and win rules are to be incorporated. Just a very basic scenario works for now.
 * Holds the specific Kalaha game logic.
 */
public class KalahaGame extends Game {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(KalahaGame.class);


    /**
     * Any specific data about the game.
     */
    private final GameData gameData;

    /**
     * Constructs a basic Kalaha game.
     *
     * @param gameConfig pit, stone and user configuration data.
     */
    protected KalahaGame(GameConfig gameConfig) {
        super(gameConfig);
        this.gameData = new GameData(gameConfig);
    }

    /**
     * TODO: Refactor this.
     * Initializes a new game.
     *
     * @return the game data.
     */
    public GameData newGame() {

        for (int i = 0; i < getNumberOfPitsPerPlayer(); i++) {

            Pit pit = new Pit(gameData.getFirstPlayer(), getNumberOfStonesPerPit());
            gameData.addPit(pit);
        }
        Kalaha kalaha1 = new Kalaha(gameData.getFirstPlayer(), 0);
        gameData.addPit(kalaha1);

        for (int i = getNumberOfPitsPerPlayer() + 1; i < getNumberOfPitsPerPlayer() * 2 + 1; i++) {

            Pit pit = new Pit(gameData.getSecondPlayer(), getNumberOfStonesPerPit());
            gameData.addPit(pit);
        }
        Kalaha kalaha2 = new Kalaha(gameData.getSecondPlayer(), 0);
        gameData.addPit(kalaha2);

        return this.gameData;
    }

    @Override
    protected void validate(PlayData playData) {

        gameData.clearViolations();
        gameData.setPlayData(playData);

        getGameRules().stream().filter(rule -> rule instanceof InputRule).forEach(rule -> rule.run(gameData));
    }

    /**
     *TODO:????
     * @param playData the turn data.
     * @return the updated game data after turn.
     */
    @Override
    public void play(PlayData playData) {

        if (gameData.hasRuleViolations()) {
            return;
        }

        getGameRules().stream().filter(rule -> rule instanceof PlayRule).forEach(rule -> rule.run(gameData));
    }

    @Override
    protected GameData getGameData() {

        if (gameData.hasRuleViolations()) {
            return gameData;
        }

        getGameRules().stream().filter(rule -> rule instanceof WinRule).forEach(rule -> rule.run(gameData));
        return gameData;
    }
}