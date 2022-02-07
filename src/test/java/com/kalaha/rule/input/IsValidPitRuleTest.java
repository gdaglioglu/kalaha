package com.kalaha.rule.input;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class IsValidPitRuleTest {

    private IsValidPitRule isValidPitRule;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameData gameData;
    private PlayData playData;

    @BeforeEach
    void setUp() {
        isValidPitRule = new IsValidPitRule();
        firstPlayer = new Player(0, "Test player 1");
        secondPlayer = new Player(1, "Test player 2");
        GameConfig gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());
        gameData = new GameData(gameConfig);
        playData = gameData.getPlayData();
    }

    @Test
    public void whenPlayDataNotSet_stopRuleProcessing() {

        gameData.setPlayData(null);
        isValidPitRule.run(gameData);
        assertFalse(gameData.hasRuleViolations(), "We don't have data to check this rule, hence no violations reported.");
    }

    @Test
    public void whenSelectedPitIndexGreaterThanPitSize_addViolation() {

        playData.setSelectedPit(15);
        playData.setPlayer(firstPlayer);

        isValidPitRule.run(gameData);
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.NON_EXISTING_PIT, gameData.getViolationInfo().get(0));
    }

    @Test
    public void whenSelectedPitIndexLessThanZero_addViolation() {

        playData.setSelectedPit(-1);
        playData.setPlayer(firstPlayer);

        isValidPitRule.run(gameData);
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.NON_EXISTING_PIT, gameData.getViolationInfo().get(0));
    }

    @Test
    public void whenFirstPlayerSelectsSelectedPitFirstPlayersKalaha_addViolation() {

        playData.setSelectedPit(6);
        playData.setPlayer(firstPlayer);

        isValidPitRule.run(gameData);
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.CANNOT_SELECT_KALAHA, gameData.getViolationInfo().get(0));
    }

    @Test
    public void whenFirstPlayerSelectsSecondPlayersKalaha_addViolation() {

        playData.setSelectedPit(13);
        playData.setPlayer(firstPlayer);

        isValidPitRule.run(gameData);
        assertEquals(2, gameData.getViolationInfo().size());
        assertEquals(Violation.CANNOT_SELECT_KALAHA, gameData.getViolationInfo().get(0));
        assertEquals(Violation.CANNOT_SELECT_OPPONENTS_PIT, gameData.getViolationInfo().get(1));
    }

    @Test
    public void whenSecondPlayerSelectsSelectedPitSecondPlayersKalaha_addViolation() {

        playData.setSelectedPit(13);
        playData.setPlayer(secondPlayer);

        isValidPitRule.run(gameData);
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.CANNOT_SELECT_KALAHA, gameData.getViolationInfo().get(0));
    }

    @Test
    public void whenSecondPlayerSelectsFirstPlayersKalaha_addViolation() {

        playData.setSelectedPit(6);
        playData.setPlayer(secondPlayer);

        isValidPitRule.run(gameData);
        assertEquals(2, gameData.getViolationInfo().size());
        assertEquals(Violation.CANNOT_SELECT_KALAHA, gameData.getViolationInfo().get(0));
        assertEquals(Violation.CANNOT_SELECT_OPPONENTS_PIT, gameData.getViolationInfo().get(1));
    }

    @Test
    public void whenFirstPlayerSelectsEmptyPit_addViolation() {

        gameData.getPits().get(4).setStones(0);
        playData.setSelectedPit(4);
        playData.setPlayer(firstPlayer);

        isValidPitRule.run(gameData);
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.NO_STONES_IN_PIT, gameData.getViolationInfo().get(0));
    }

    @Test
    public void whenSecondPlayerSelectsEmptyPit_addViolation() {

        gameData.getPits().get(7).setStones(0);
        playData.setSelectedPit(7);
        playData.setPlayer(secondPlayer);

        isValidPitRule.run(gameData);
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.NO_STONES_IN_PIT, gameData.getViolationInfo().get(0));
    }

    @Test
    public void whenFirstPlayerSelectsValidPit_noViolation() {

        playData.setSelectedPit(3);
        playData.setPlayer(firstPlayer);

        isValidPitRule.run(gameData);
        assertFalse(gameData.hasRuleViolations(),"Player should be allowed to select one of his own pits when it has stones!");
    }

    @Test
    public void whenSecondPlayerSelectsValidPit_noViolation() {

        playData.setSelectedPit(10);
        playData.setPlayer(secondPlayer);

        isValidPitRule.run(gameData);
        assertFalse(gameData.hasRuleViolations(),"Player should be allowed to select one of his own pits when it has stones!");
    }

}