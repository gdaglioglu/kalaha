package com.kalaha.rule.input;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class IsGameOngoingRuleTest {

    private IsGameOngoingRule isGameOngoingRule;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameConfig gameConfig;
    private GameData gameData;

    @BeforeEach
    void setUp() {
        isGameOngoingRule = new IsGameOngoingRule();
        firstPlayer = new Player(0, "Test player 1");
        secondPlayer = new Player(1, "Test player 2");
        gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());
        gameData = new GameData(gameConfig);
    }

    @Test
    public void gameIsOngoing() {

        assertEquals(GameStatus.ONGOING, gameData.getGameInfo().getGameStatus());

        isGameOngoingRule.run(gameData);
        assertFalse(gameData.hasRuleViolations(), "Game is in ongoing status, hence no validations should be reported");
    }

    @Test
    public void gameIsFinished() {

        gameData.getGameInfo().setWinner(firstPlayer);
        assertEquals(GameStatus.FINISHED, gameData.getGameInfo().getGameStatus());

        isGameOngoingRule.run(gameData);
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.GAME_ALREADY_OVER, gameData.getViolationInfo().get(0));
    }
}