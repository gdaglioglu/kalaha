package com.kalaha.util;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PitUtilTest {

    private GameData gameData;
    private Player secondPlayer;
    private Player firstPlayer;

    @BeforeEach
    void setUp() {
        firstPlayer = new Player(0, "Test player 1");
        secondPlayer = new Player(1, "Test player 2");
        GameConfig gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());
        gameData = new GameData(gameConfig);
    }

    @Test
    public void whenFirstPlayersPitsSelected_findOppositePit() {

        gameData.setCurrentIndex(0);
        Pit oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(12, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(1);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(11, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(2);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(10, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(3);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(9, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(4);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(8, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(5);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(7, gameData.getPits().indexOf(oppositePit));
    }

    @Test
    public void whenSecondPlayersPitsSelected_findOppositePit() {

        gameData.setCurrentIndex(7);
        Pit oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(5, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(8);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(4, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(9);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(3, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(10);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(2, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(11);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(1, gameData.getPits().indexOf(oppositePit));

        gameData.setCurrentIndex(12);
        oppositePit = PitUtil.getOppositePit(gameData);
        assertEquals(0, gameData.getPits().indexOf(oppositePit));
    }

    @Test
    public void whenFirstPlayersFirstPitSelected_getNextPit_bringsFirstPlayersSecondPit() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(firstPlayer);

        List<Pit> pits = gameData.getPits();
        Pit pit = pits.get(0);
        Pit nextPit = PitUtil.getNextPit(gameData, pit);
        assertEquals(pits.get(1), nextPit);
        assertEquals(1, gameData.getCurrentIndex());
    }

    @Test
    public void whenFirstPlayersLastPitSelected_getNextPit_bringsFirstPlayersKalaha() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(firstPlayer);

        List<Pit> pits = gameData.getPits();
        Pit pit = pits.get(5);
        Pit nextPit = PitUtil.getNextPit(gameData, pit);
        assertTrue(nextPit instanceof Kalaha, "Player's own kalaha should have been returned!");
        assertEquals(pits.get(6), nextPit);
        assertEquals(6, gameData.getCurrentIndex());
    }

    @Test
    public void whenFirstPlayersKalahaSelected_getNextPit_bringsSecondPlayersFirstPit() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(firstPlayer);

        List<Pit> pits = gameData.getPits();
        Pit pit = pits.get(6);
        Pit nextPit = PitUtil.getNextPit(gameData, pit);
        assertEquals(pits.get(7), nextPit);
        assertEquals(secondPlayer, nextPit.getPlayer());
        assertEquals(7, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayersFirstPitSelected_getNextPit_bringsSecondPlayersFirstPit() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(secondPlayer);

        List<Pit> pits = gameData.getPits();
        Pit pit = pits.get(7);
        Pit nextPit = PitUtil.getNextPit(gameData, pit);
        assertEquals(pits.get(8), nextPit);
        assertEquals(8, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayersLastPitSelected_getNextPit_bringsSecondPlayersKalaha() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(secondPlayer);

        List<Pit> pits = gameData.getPits();
        Pit pit = pits.get(12);
        Pit nextPit = PitUtil.getNextPit(gameData, pit);
        assertEquals(pits.get(13), nextPit);
        assertTrue(nextPit instanceof Kalaha, "Player's own kalaha should have been returned!");
        assertEquals(13, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayersKalahaSelected_getNextPit_bringsFirstPlayersFirstPit() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(secondPlayer);

        List<Pit> pits = gameData.getPits();
        Pit pit = pits.get(13);
        Pit nextPit = PitUtil.getNextPit(gameData, pit);

        assertEquals(pits.get(0), nextPit);
        assertEquals(firstPlayer, nextPit.getPlayer());
        assertEquals(0, gameData.getCurrentIndex());
    }

    @Test
    public void whenFirstPlayerPlayed_getNextPit_reachesSecondPlayersKalaha_shouldBeSkipped() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(firstPlayer);

        List<Pit> pits = gameData.getPits();
        Pit pit = pits.get(12);
        Pit nextPit = PitUtil.getNextPit(gameData, pit);

        assertEquals(pits.get(0), nextPit);
        assertEquals(0, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayerPlayed_getNextPit_reachesFirstPlayersKalaha_shouldBeSkipped() {

        PlayData playData = gameData.getPlayData();
        playData.setPlayer(secondPlayer);

        List<Pit> pits = gameData.getPits();
        Pit pit = pits.get(5);
        Pit nextPit = PitUtil.getNextPit(gameData, pit);

        assertEquals(pits.get(7), nextPit);
        assertEquals(7, gameData.getCurrentIndex());
    }
}