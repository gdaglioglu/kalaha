package com.kalaha.rule.input;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class IsPlayersTurnRuleTest {

    private IsPlayersTurnRule isPlayersTurnRule;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameData gameData;

    @BeforeEach
    void setUp() {
        isPlayersTurnRule = new IsPlayersTurnRule();
        firstPlayer = new Player(0, "Test player 1");
        secondPlayer = new Player(1, "Test player 2");
        GameConfig gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());
        gameData = new GameData(gameConfig);
    }

    @Test
    public void isPlayersTurn() {

        assertEquals(firstPlayer, gameData.getTurnInfo().whoseTurn());

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(firstPlayer);
        playData.setSelectedPit(0);

        isPlayersTurnRule.run(gameData);
        assertFalse(gameData.hasRuleViolations(), "It's first players turn, hence no validations should be reported");
    }

    @Test
    public void isNotPlayersTurn() {

        assertEquals(firstPlayer, gameData.getTurnInfo().whoseTurn());

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(secondPlayer);
        playData.setSelectedPit(8);

        isPlayersTurnRule.run(gameData);
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.NOT_PLAYERS_TURN, gameData.getViolationInfo().get(0));
    }

}