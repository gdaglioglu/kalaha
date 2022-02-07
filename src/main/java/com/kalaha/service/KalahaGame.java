package com.kalaha.service;

import com.kalaha.model.*;
import com.kalaha.rule.input.InputRule;
import com.kalaha.rule.play.PlayRule;
import com.kalaha.rule.win.WinRule;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a kalaha game.
 */
@Getter
public class KalahaGame extends Game {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(KalahaGame.class);

    /**
     * Anything about game state.
     */
    private final GameData gameData;

    /**
     * Constructs a basic Kalaha game.
     *
     * @param gameConfig the configuration data to base the game on.
     */
    protected KalahaGame(GameConfig gameConfig) {
        super(gameConfig);
        this.gameData = new GameData(gameConfig);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prePlay(PlayData playData) {

        gameData.clearViolations();
        gameData.setPlayData(playData);

        getGameRules().stream().filter(rule -> rule instanceof InputRule).forEach(rule -> rule.run(gameData));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(PlayData playData) {

        if (gameData.hasRuleViolations()) {
            return;
        }

        getGameRules().stream().filter(rule -> rule instanceof PlayRule).forEach(rule -> rule.run(gameData));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GameData postPlay() {

        if (gameData.hasRuleViolations()) {
            return gameData;
        }

        getGameRules().stream().filter(rule -> rule instanceof WinRule).forEach(rule -> rule.run(gameData));
        return gameData;
    }
}