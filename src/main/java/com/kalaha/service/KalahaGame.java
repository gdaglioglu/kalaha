package com.kalaha.service;

import com.kalaha.model.*;
import com.kalaha.rule.GameRule;
import com.kalaha.rule.input.InputRule;
import com.kalaha.rule.input.IsGameOngoingRule;
import com.kalaha.rule.input.IsPlayersTurnRule;
import com.kalaha.rule.input.IsValidPitRule;
import com.kalaha.rule.play.CollectStonesFromOppositePitRule;
import com.kalaha.rule.play.DeterminePlayerTurnRule;
import com.kalaha.rule.play.DistributeStonesRule;
import com.kalaha.rule.play.PlayRule;
import com.kalaha.rule.win.AreAllPitsEmptyForEitherPlayer;
import com.kalaha.rule.win.WinRule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a kalaha game.
 */
@Slf4j
public class KalahaGame extends Game {

    /**
     * Anything about game state.
     */
    @Getter
    private final GameData gameData;

    /**
     * Rules that are specific to kalaha game.
     */
    private final List<GameRule> gameRules;

    /**
     * Constructs a basic Kalaha game.
     *
     * @param gameConfig the configuration data to base the game on.
     */
    protected KalahaGame(GameConfig gameConfig) {

        gameRules = new ArrayList<>();
        gameRules.add(new IsGameOngoingRule());
        gameRules.add(new IsPlayersTurnRule());
        gameRules.add(new IsValidPitRule());

        gameRules.add(new DistributeStonesRule());
        gameRules.add(new CollectStonesFromOppositePitRule());
        gameRules.add(new DeterminePlayerTurnRule());

        gameRules.add(new AreAllPitsEmptyForEitherPlayer());

        gameConfig.setRules(gameRules);

        this.gameData = new GameData(gameConfig);
        log.info("Kalaha game is initialized with {} pits and {} stones per pit",
                gameConfig.getNumberOfPitsPerPlayer(), gameConfig.getNumberOfStonesPerPit());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isValidPlay(PlayData playData) {

        gameData.clearViolations();
        gameData.setPlayData(playData);

        gameRules.stream().filter(rule -> rule instanceof InputRule).forEach(rule -> rule.run(gameData));

        return !gameData.hasRuleViolations();
    }

    /**
     * {@inheritDoc}
     */
    protected void play(PlayData playData) {

        gameRules.stream().filter(rule -> rule instanceof PlayRule).forEach(rule -> rule.run(gameData));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void postPlay() {

        gameRules.stream().filter(rule -> rule instanceof WinRule).forEach(rule -> rule.run(gameData));
    }
}