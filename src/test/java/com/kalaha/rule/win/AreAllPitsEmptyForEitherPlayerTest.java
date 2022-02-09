package com.kalaha.rule.win;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AreAllPitsEmptyForEitherPlayerTest {

    private AreAllPitsEmptyForEitherPlayer areAllPitsEmptyForEitherPlayer;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameData gameData;

    @BeforeEach
    void setUp() {
        areAllPitsEmptyForEitherPlayer = new AreAllPitsEmptyForEitherPlayer();
        firstPlayer = new Player(0, "Test player 1");
        secondPlayer = new Player(1, "Test player 2");
        GameConfig gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());
        gameData = new GameData(gameConfig);
    }

    @Test
    public void whenBothPlayersHaveStonesLeft_gameContinues() {

        areAllPitsEmptyForEitherPlayer.run(gameData);

        GameInfo gameInfo = gameData.getGameInfo();
        assertEquals(GameStatus.ONGOING, gameInfo.getGameStatus());
        assertNull(gameInfo.getWinner(), "Game ongoing, winner should not be set!");
    }

    @Test
    public void whenFirstPlayerHaveNoStonesLeft_gameFinished_firstPlayerWins() {

        List<Pit> pits = gameData.getPits();
        pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).forEach(pit -> pit.setStones(0));
        pits.get(6).setStones(40);

        areAllPitsEmptyForEitherPlayer.run(gameData);

        GameInfo gameInfo = gameData.getGameInfo();
        pits = gameData.getPits();

        int firstPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).mapToInt(Pit::getStones).sum();
        int secondPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).mapToInt(Pit::getStones).sum();

        assertEquals(40, pits.get(6).getStones());
        assertEquals(36, pits.get(13).getStones());
        assertEquals(pits.get(6).getStones(), firstPlayersOverallStoneCount);
        assertEquals(pits.get(13).getStones(), secondPlayersOverallStoneCount);
        assertEquals(GameStatus.FINISHED, gameInfo.getGameStatus());
        assertEquals(firstPlayer, gameInfo.getWinner());
    }

    @Test
    public void whenFirstPlayerHaveNoStonesLeft_gameFinished_secondPlayerWins() {

        List<Pit> pits = gameData.getPits();
        pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).forEach(pit -> pit.setStones(0));
        pits.get(6).setStones(25);

        areAllPitsEmptyForEitherPlayer.run(gameData);

        GameInfo gameInfo = gameData.getGameInfo();
        pits = gameData.getPits();

        int firstPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).mapToInt(Pit::getStones).sum();
        int secondPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).mapToInt(Pit::getStones).sum();

        assertEquals(25, pits.get(6).getStones());
        assertEquals(36, pits.get(13).getStones());
        assertEquals(pits.get(6).getStones(), firstPlayersOverallStoneCount);
        assertEquals(36, secondPlayersOverallStoneCount);
        assertEquals(GameStatus.FINISHED, gameInfo.getGameStatus());
        assertEquals(secondPlayer, gameInfo.getWinner());
    }

    @Test
    public void whenSecondPlayerHaveNoStonesLeft_gameFinished_secondPlayerWins() {

        List<Pit> pits = gameData.getPits();
        pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).forEach(pit -> pit.setStones(0));
        pits.get(13).setStones(40);

        areAllPitsEmptyForEitherPlayer.run(gameData);

        GameInfo gameInfo = gameData.getGameInfo();
        pits = gameData.getPits();

        int firstPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).mapToInt(Pit::getStones).sum();
        int secondPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).mapToInt(Pit::getStones).sum();

        assertEquals(36, pits.get(6).getStones());
        assertEquals(40, pits.get(13).getStones());
        assertEquals(pits.get(6).getStones(), firstPlayersOverallStoneCount);
        assertEquals(pits.get(13).getStones(), secondPlayersOverallStoneCount);
        assertEquals(GameStatus.FINISHED, gameInfo.getGameStatus());
        assertEquals(secondPlayer, gameInfo.getWinner());
    }

    @Test
    public void whenSecondPlayerHaveNoStonesLeft_gameFinished_firstPlayerWins() {

        List<Pit> pits = gameData.getPits();
        pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).forEach(pit -> pit.setStones(0));
        pits.get(13).setStones(25);

        areAllPitsEmptyForEitherPlayer.run(gameData);

        GameInfo gameInfo = gameData.getGameInfo();
        pits = gameData.getPits();

        int firstPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).mapToInt(Pit::getStones).sum();
        int secondPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).mapToInt(Pit::getStones).sum();

        assertEquals(36, pits.get(6).getStones());
        assertEquals(25, pits.get(13).getStones());
        assertEquals(36, firstPlayersOverallStoneCount);
        assertEquals(pits.get(13).getStones(), secondPlayersOverallStoneCount);
        assertEquals(GameStatus.FINISHED, gameInfo.getGameStatus());
        assertEquals(firstPlayer, gameInfo.getWinner());
    }

    @Test
    public void whenFirstPlayerHaveNoStonesLeft_gameFinished_withDraw() {

        List<Pit> pits = gameData.getPits();
        pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).forEach(pit -> pit.setStones(0));
        pits.get(6).setStones(36);

        areAllPitsEmptyForEitherPlayer.run(gameData);

        GameInfo gameInfo = gameData.getGameInfo();
        pits = gameData.getPits();

        int firstPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).mapToInt(Pit::getStones).sum();
        int secondPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).mapToInt(Pit::getStones).sum();

        assertEquals(36, pits.get(6).getStones());
        assertEquals(36, pits.get(13).getStones());
        assertEquals(36, firstPlayersOverallStoneCount);
        assertEquals(pits.get(13).getStones(), secondPlayersOverallStoneCount);
        assertEquals(GameStatus.FINISHED, gameInfo.getGameStatus());
        assertNull(gameInfo.getWinner());
    }

    @Test
    public void whenSecondPlayerHaveNoStonesLeft_gameFinished_withDraw() {

        List<Pit> pits = gameData.getPits();
        pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).forEach(pit -> pit.setStones(0));
        pits.get(13).setStones(36);

        areAllPitsEmptyForEitherPlayer.run(gameData);

        GameInfo gameInfo = gameData.getGameInfo();
        pits = gameData.getPits();

        int firstPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).mapToInt(Pit::getStones).sum();
        int secondPlayersOverallStoneCount = pits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).mapToInt(Pit::getStones).sum();

        assertEquals(36, pits.get(6).getStones());
        assertEquals(36, pits.get(13).getStones());
        assertEquals(36, firstPlayersOverallStoneCount);
        assertEquals(pits.get(13).getStones(), secondPlayersOverallStoneCount);
        assertEquals(GameStatus.FINISHED, gameInfo.getGameStatus());
        assertNull(gameInfo.getWinner());
    }

}