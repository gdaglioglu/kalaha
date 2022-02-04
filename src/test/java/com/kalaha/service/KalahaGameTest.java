package com.kalaha.service;

import com.kalaha.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class KalahaGameTest {

    private final KalahaGame kalahaGameWith6StonesIn6Pits = new KalahaGame(6,6);

    @Test
    void newGameCreated() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.newGame();
        assertNotNull(returnedGameData, "Game creation failed");
        assertEquals(0, returnedGameData.getCurrentIndex());
        assertEquals(GameState.TURN_P1, returnedGameData.getGameState());
    }

    @Test
    void newGameCreatedWithCorrectPitStructure() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.newGame();
        List<Pit> returnedPits = returnedGameData.getPits();

        assertEquals(14, returnedPits.size());

        List<Pit> pitList = returnedPits.stream().filter(pit -> !(pit instanceof Kalaha)).collect(Collectors.toList());

        assertTrue(returnedPits.get(returnedPits.size() - 1) instanceof Kalaha, "Kalaha not found in expected index");
        assertTrue(returnedPits.get(returnedPits.size() / 2 - 1) instanceof Kalaha, "Kalaha not found in expected index");
        assertEquals(returnedPits.size() - 2, pitList.size());
    }

    @Test
    void newGameCreatedWithCorrectKalahaData() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.newGame();
        List<Pit> returnedPits = returnedGameData.getPits();
        List<Pit> kalahaList = returnedPits.stream().filter(pit -> pit instanceof Kalaha).collect(Collectors.toList());

        Kalaha firstPlayersKalaha = (Kalaha) kalahaList.get(0);
        Kalaha secondPlayersKalaha = (Kalaha) kalahaList.get(1);

        Player firstPlayer = new Player(0, "Player 1");
        Player secondPlayer = new Player(1, "Player 2");

        assertEquals(firstPlayer, firstPlayersKalaha.getPlayer());
        assertEquals(0, firstPlayersKalaha.getStones());

        assertEquals(secondPlayer, secondPlayersKalaha.getPlayer());
        assertEquals(0, secondPlayersKalaha.getStones());
    }

    @Test
    void newGameCreatedWithCorrectPitData() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.newGame();
        List<Pit> returnedPits = returnedGameData.getPits();
        List<Pit> pitList = returnedPits.stream().filter(pit -> !(pit instanceof Kalaha)).collect(Collectors.toList());

        List<Pit> pitsWithDefaultNumberOfStones = pitList.stream().filter(pit -> pit.getStones() == 6).collect(Collectors.toList());
        assertEquals(pitList.size(), pitsWithDefaultNumberOfStones.size());

        Player firstPlayer = new Player(0, "Player 1");
        Player secondPlayer = new Player(1, "Player 2");

        List<Pit> firstPlayersPits = IntStream.range(0, pitList.size() / 2 - 1).mapToObj(i -> pitList.get(i)).collect(Collectors.toList());
        assertEquals(firstPlayersPits.size(), firstPlayersPits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).count());

        List<Pit> secondPlayersPits = IntStream.range(pitList.size() / 2, pitList.size()).mapToObj(i -> pitList.get(i)).collect(Collectors.toList());
        assertEquals(secondPlayersPits.size(), secondPlayersPits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).count());
    }

    @Test
    void playForPlayer1AndPit1() {

        PlayData playData = new PlayData(0,0);

        kalahaGameWith6StonesIn6Pits.newGame();
        GameData gameData = kalahaGameWith6StonesIn6Pits.play(playData);

        List<Pit> pits = gameData.getPits();
        assertEquals(0,pits.get(0).getStones());
        assertEquals(7,pits.get(1).getStones());
        assertEquals(7,pits.get(2).getStones());
        assertEquals(7,pits.get(3).getStones());
        assertEquals(7,pits.get(4).getStones());
        assertEquals(7,pits.get(5).getStones());
        assertEquals(1,pits.get(6).getStones());
        assertEquals(6,pits.get(7).getStones());
        assertEquals(6,pits.get(8).getStones());
        assertEquals(6,pits.get(9).getStones());
        assertEquals(6,pits.get(10).getStones());
        assertEquals(6,pits.get(11).getStones());
        assertEquals(6,pits.get(12).getStones());
        assertEquals(0,pits.get(13).getStones());
        assertEquals(6,gameData.getCurrentIndex());
        assertEquals(GameState.TURN_P1,gameData.getGameState());
    }

    @Test
    void playForPlayer1AndPit5() {

        PlayData playData = new PlayData(5,0);

        kalahaGameWith6StonesIn6Pits.newGame();
        GameData gameData = kalahaGameWith6StonesIn6Pits.play(playData);

        List<Pit> pits = gameData.getPits();
        assertEquals(6,pits.get(0).getStones());
        assertEquals(6,pits.get(1).getStones());
        assertEquals(6,pits.get(2).getStones());
        assertEquals(6,pits.get(3).getStones());
        assertEquals(6,pits.get(4).getStones());
        assertEquals(0,pits.get(5).getStones());
        assertEquals(1,pits.get(6).getStones());
        assertEquals(7,pits.get(7).getStones());
        assertEquals(7,pits.get(8).getStones());
        assertEquals(7,pits.get(9).getStones());
        assertEquals(7,pits.get(10).getStones());
        assertEquals(7,pits.get(11).getStones());
        assertEquals(6,pits.get(12).getStones());
        assertEquals(0,pits.get(13).getStones());
        assertEquals(11,gameData.getCurrentIndex());
        assertEquals(GameState.TURN_P2,gameData.getGameState());
    }

    @Test
    void playForPlayer2AndPit2() {

        PlayData playData = new PlayData(8,1);

        kalahaGameWith6StonesIn6Pits.newGame();
        GameData gameData = kalahaGameWith6StonesIn6Pits.play(playData);

        List<Pit> pits = gameData.getPits();
        assertEquals(7,pits.get(0).getStones());
        assertEquals(6,pits.get(1).getStones());
        assertEquals(6,pits.get(2).getStones());
        assertEquals(6,pits.get(3).getStones());
        assertEquals(6,pits.get(4).getStones());
        assertEquals(6,pits.get(5).getStones());
        assertEquals(0,pits.get(6).getStones());
        assertEquals(6,pits.get(7).getStones());
        assertEquals(0,pits.get(8).getStones());
        assertEquals(7,pits.get(9).getStones());
        assertEquals(7,pits.get(10).getStones());
        assertEquals(7,pits.get(11).getStones());
        assertEquals(7,pits.get(12).getStones());
        assertEquals(1,pits.get(13).getStones());
        assertEquals(0,gameData.getCurrentIndex());
        assertEquals(GameState.TURN_P1,gameData.getGameState());
    }

    @Test
    void playForPlayer2AndPit1() {

        PlayData playData = new PlayData(7,1);

        kalahaGameWith6StonesIn6Pits.newGame();
        GameData gameData = kalahaGameWith6StonesIn6Pits.play(playData);

        List<Pit> pits = gameData.getPits();
        assertEquals(6,pits.get(0).getStones());
        assertEquals(6,pits.get(1).getStones());
        assertEquals(6,pits.get(2).getStones());
        assertEquals(6,pits.get(3).getStones());
        assertEquals(6,pits.get(4).getStones());
        assertEquals(6,pits.get(5).getStones());
        assertEquals(0,pits.get(6).getStones());
        assertEquals(0,pits.get(7).getStones());
        assertEquals(7,pits.get(8).getStones());
        assertEquals(7,pits.get(9).getStones());
        assertEquals(7,pits.get(10).getStones());
        assertEquals(7,pits.get(11).getStones());
        assertEquals(7,pits.get(12).getStones());
        assertEquals(1,pits.get(13).getStones());
        assertEquals(13,gameData.getCurrentIndex());
        assertEquals(GameState.TURN_P2,gameData.getGameState());
    }
}