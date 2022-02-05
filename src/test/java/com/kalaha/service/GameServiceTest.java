package com.kalaha.service;

import com.kalaha.model.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {


    private static final GameService gameService = new GameService();
    GameConfig gameConfig = new GameConfig("Player 1 name", "Player 2 name", 6,6, Collections.emptyList());

    @Test
    void newGameCreated() {

        GameData returnedGameData = gameService.newGame(gameConfig);
        assertNotNull(returnedGameData, "Game creation failed");
        assertEquals(0, returnedGameData.getCurrentIndex());
        assertEquals(TurnInfo.TurnStatus.TURN_P1, returnedGameData.getTurnInfo().getTurnStatus());
    }

    @Test
    void newGameCreatedWithCorrectPitStructure() {

        GameData returnedGameData = gameService.newGame(gameConfig);
        List<Pit> returnedPits = returnedGameData.getPits();

        assertEquals(14, returnedPits.size());

        List<Pit> pitList = returnedPits.stream().filter(pit -> !(pit instanceof Kalaha)).collect(Collectors.toList());

        assertTrue(returnedPits.get(returnedPits.size() - 1) instanceof Kalaha, "Kalaha not found in expected index");
        assertTrue(returnedPits.get(returnedPits.size() / 2 - 1) instanceof Kalaha, "Kalaha not found in expected index");
        assertEquals(returnedPits.size() - 2, pitList.size());
    }

    @Test
    void newGameCreatedWithCorrectKalahaData() {

        GameData returnedGameData = gameService.newGame(gameConfig);
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

        GameData returnedGameData = gameService.newGame(gameConfig);
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
    void play() {

    }

    @Test
    void getGame() {
    }

}