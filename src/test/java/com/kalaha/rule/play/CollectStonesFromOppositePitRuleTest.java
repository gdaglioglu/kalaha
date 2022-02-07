package com.kalaha.rule.play;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectStonesFromOppositePitRuleTest {

    private CollectStonesFromOppositePitRule collectStonesFromOppositePitRule;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameData gameData;
    private PlayData playData;

    @BeforeEach
    void setUp() {
        collectStonesFromOppositePitRule = new CollectStonesFromOppositePitRule();
        firstPlayer = new Player(0, "Test player 1");
        secondPlayer = new Player(1, "Test player 2");
        GameConfig gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());
        gameData = new GameData(gameConfig);
        playData = gameData.getPlayData();
    }

    @Test
    public void whenFirstPlayersLastStone_endsUpInFirstPlayersEmptyPit_moveStonesToKalaha() {

        List<Pit> pits = gameData.getPits();
        pits.get(5).setStones(1);

        playData.setPlayer(firstPlayer);
        gameData.setCurrentIndex(5);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(0, pits.get(5).getStones());
        assertEquals(7, pits.get(6).getStones());
        assertEquals(0, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(6, gameData.getCurrentIndex());
    }

    @Test
    public void whenFirstPlayersLastStone_endsUpInFirstPlayersNonEmptyPit() {

        List<Pit> pits = gameData.getPits();
        pits.get(5).setStones(2);

        playData.setPlayer(firstPlayer);
        gameData.setCurrentIndex(5);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(2, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(5, gameData.getCurrentIndex());
    }

    @Test
    public void whenFirstPlayersLastStone_endsUpInFirstPlayersEmptyKalaha() {

        List<Pit> pits = gameData.getPits();
        pits.get(6).setStones(1);

        playData.setPlayer(firstPlayer);
        gameData.setCurrentIndex(6);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
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
    public void whenFirstPlayersLastStone_endsUpInSecondPlayersEmptyPit() {

        List<Pit> pits = gameData.getPits();
        pits.get(12).setStones(1);

        playData.setPlayer(firstPlayer);
        gameData.setCurrentIndex(12);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(1, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(12, gameData.getCurrentIndex());
    }

    @Test
    public void whenFirstPlayersLastStone_endsUpInSecondPlayersNonEmptyPit() {

        List<Pit> pits = gameData.getPits();
        pits.get(9).setStones(4);

        playData.setPlayer(firstPlayer);
        gameData.setCurrentIndex(9);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(4, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(9, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayersLastStone_endsUpInSecondPlayersEmptyPit_moveStonesToKalaha() {

        List<Pit> pits = gameData.getPits();
        pits.get(10).setStones(1);

        playData.setPlayer(secondPlayer);
        gameData.setCurrentIndex(10);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(0, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(0, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(7, pits.get(13).getStones());
        assertEquals(13, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayersLastStone_endsUpInSecondPlayersNonEmptyPit() {

        List<Pit> pits = gameData.getPits();
        pits.get(11).setStones(2);

        playData.setPlayer(secondPlayer);
        gameData.setCurrentIndex(11);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(2, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(11, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayersLastStone_endsUpInSecondPlayersEmptyKalaha() {

        List<Pit> pits = gameData.getPits();
        pits.get(13).setStones(1);

        playData.setPlayer(secondPlayer);
        gameData.setCurrentIndex(13);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(1, pits.get(13).getStones());
        assertEquals(13, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayersLastStone_endsUpInFirstPlayersEmptyPit() {

        List<Pit> pits = gameData.getPits();
        pits.get(3).setStones(1);

        playData.setPlayer(secondPlayer);
        gameData.setCurrentIndex(3);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(1, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(3, gameData.getCurrentIndex());
    }

    @Test
    public void whenSecondPlayersLastStone_endsUpInFirstPlayersNonEmptyPit() {

        List<Pit> pits = gameData.getPits();
        pits.get(1).setStones(4);

        playData.setPlayer(secondPlayer);
        gameData.setCurrentIndex(1);

        collectStonesFromOppositePitRule.run(gameData);
        assertEquals(6, pits.get(0).getStones());
        assertEquals(4, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(6, pits.get(10).getStones());
        assertEquals(6, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(0, pits.get(13).getStones());
        assertEquals(1, gameData.getCurrentIndex());
    }

}