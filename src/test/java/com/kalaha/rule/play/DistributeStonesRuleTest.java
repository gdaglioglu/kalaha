package com.kalaha.rule.play;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistributeStonesRuleTest {

    private DistributeStonesRule distributeStonesRule;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameData gameData;

    @BeforeEach
    void setUp() {
        distributeStonesRule = new DistributeStonesRule();
        firstPlayer = new Player(0, "Test player 1");
        secondPlayer = new Player(1, "Test player 2");
        GameConfig gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());
        gameData = new GameData(gameConfig);
    }

    @Test
    public void whenFirstPlayerSelectsFirstPit() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(firstPlayer);
        playData.setSelectedPit(0);
        distributeStonesRule.run(gameData);

        List<Pit> pits = gameData.getPits();
        assertEquals(0, pits.get(0).getStones());
        assertEquals(7, pits.get(1).getStones());
        assertEquals(7, pits.get(2).getStones());
        assertEquals(7, pits.get(3).getStones());
        assertEquals(7, pits.get(4).getStones());
        assertEquals(7, pits.get(5).getStones());
        assertEquals(1, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(6, gameData.getCurrentIndex());
    }

    @Test
    public void whenFirstPlayerSelectsSixthPit() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(firstPlayer);
        playData.setSelectedPit(5);
        distributeStonesRule.run(gameData);

        List<Pit> pits = gameData.getPits();
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(0, pits.get(5).getStones());
        assertEquals(1, pits.get(6).getStones());
        assertEquals(7, pits.get(7).getStones());
        assertEquals(7, pits.get(8).getStones());
        assertEquals(7, pits.get(9).getStones());
        assertEquals(7, pits.get(10).getStones());
        assertEquals(7, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(11, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayerSelectsSecondPit() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(secondPlayer);
        playData.setSelectedPit(8);
        distributeStonesRule.run(gameData);

        List<Pit> pits = gameData.getPits();
        assertEquals(7, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(0, pits.get(8).getStones());
        assertEquals(7, pits.get(9).getStones());
        assertEquals(7, pits.get(10).getStones());
        assertEquals(7, pits.get(11).getStones());
        assertEquals(7, pits.get(12).getStones());
        assertEquals(1, pits.get(13).getStones());
        assertEquals(0, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayerSelectsFifthPit() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(secondPlayer);
        playData.setSelectedPit(11);
        distributeStonesRule.run(gameData);

        List<Pit> pits = gameData.getPits();
        assertEquals(7, pits.get(0).getStones());
        assertEquals(7, pits.get(1).getStones());
        assertEquals(7, pits.get(2).getStones());
        assertEquals(7, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(0, pits.get(11).getStones());
        assertEquals(7, pits.get(12).getStones());
        assertEquals(1, pits.get(13).getStones());
        assertEquals(3, gameData.getCurrentIndex());
    }
}