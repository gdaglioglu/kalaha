package com.kalaha.service;

import com.kalaha.model.*;
import com.kalaha.rule.GameRule;
import com.kalaha.rule.input.IsGameOngoingRule;
import com.kalaha.rule.input.IsPlayersTurnRule;
import com.kalaha.rule.input.IsValidPitRule;
import com.kalaha.rule.play.CollectStonesFromOppositePitRule;
import com.kalaha.rule.play.DeterminePlayerTurnRule;
import com.kalaha.rule.play.DistributeStonesRule;
import com.kalaha.rule.win.AreAllPitsEmptyForEitherPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class KalahaGameTest {


    private KalahaGame kalahaGameWith6StonesIn6Pits;

    private Player player1;
    private Player player2;


    @BeforeEach
    void setUp() {
        List<GameRule> rules = new ArrayList<>();
        rules.add(new IsGameOngoingRule());
        rules.add(new IsPlayersTurnRule());
        rules.add(new IsValidPitRule());

        rules.add(new DistributeStonesRule());
        rules.add(new CollectStonesFromOppositePitRule());
        rules.add(new DeterminePlayerTurnRule());

        rules.add(new AreAllPitsEmptyForEitherPlayer());

        GameConfig gameConfig = new GameConfig("Player 1 name","Player 2 name",6,6,rules);
        kalahaGameWith6StonesIn6Pits = new KalahaGame(gameConfig);

        player1 = new Player(0, "Player 1 name");
        player2 = new Player(1, "Player 2 name");
    }

    @Test
    void newGameCreated() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.postPlay();
        assertNotNull(returnedGameData, "Game creation failed");
        assertEquals(0, returnedGameData.getCurrentIndex());
        assertEquals(GameStatus.ONGOING, returnedGameData.getGameInfo().getGameStatus());
        assertNull(returnedGameData.getGameInfo().getWinner(), "No winner should have been established yet!");
        assertEquals(returnedGameData.getPits().get(0).getPlayer(), returnedGameData.getTurnInfo().whoseTurn());
        assertEquals(player1, returnedGameData.getTurnInfo().whoseTurn());
    }

    @Test
    void newGameCreatedWithCorrectPitStructure() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.postPlay();
        List<Pit> returnedPits = returnedGameData.getPits();

        assertEquals(14, returnedPits.size());

        List<Pit> pitList = returnedPits.stream().filter(pit -> !(pit instanceof Kalaha)).collect(Collectors.toList());

        assertTrue(returnedPits.get(returnedPits.size() - 1) instanceof Kalaha, "Kalaha not found in expected index");
        assertTrue(returnedPits.get(returnedPits.size() / 2 - 1) instanceof Kalaha, "Kalaha not found in expected index");
        assertEquals(returnedPits.size() - 2, pitList.size());
    }

    @Test
    void newGameCreatedWithCorrectKalahaData() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.postPlay();
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

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.postPlay();
        List<Pit> returnedPits = returnedGameData.getPits();
        List<Pit> pitList = returnedPits.stream().filter(pit -> !(pit instanceof Kalaha)).collect(Collectors.toList());

        List<Pit> pitsWithDefaultNumberOfStones = pitList.stream().filter(pit -> pit.getStones() == 6).collect(Collectors.toList());
        assertEquals(pitList.size(), pitsWithDefaultNumberOfStones.size());

        Player firstPlayer = new Player(0, "Player 1");
        Player secondPlayer = new Player(1, "Player 2");

        List<Pit> firstPlayersPits = IntStream.range(0, pitList.size() / 2 - 1).mapToObj(pitList::get).collect(Collectors.toList());
        assertEquals(firstPlayersPits.size(), firstPlayersPits.stream().filter(pit -> pit.getPlayer().equals(firstPlayer)).count());

        List<Pit> secondPlayersPits = IntStream.range(pitList.size() / 2, pitList.size()).mapToObj(pitList::get).collect(Collectors.toList());
        assertEquals(secondPlayersPits.size(), secondPlayersPits.stream().filter(pit -> pit.getPlayer().equals(secondPlayer)).count());
    }

    @Test
    void playTurnForPlayer1AndPit1() {

        PlayData playData = new PlayData(0, player1);

        kalahaGameWith6StonesIn6Pits.postPlay();
        GameData gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

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
        assertEquals(player1, gameData.getTurnInfo().whoseTurn());
    }

    @Test
    void playForPlayer1AndPit5() {

        PlayData playData = new PlayData(5, player1);

        kalahaGameWith6StonesIn6Pits.postPlay();
        GameData gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

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
        assertEquals(player2, gameData.getTurnInfo().whoseTurn());
    }

    @Test
    void playForPlayer2AndPit2() {

        PlayData playData = new PlayData(8, player2);

        GameData gameData = kalahaGameWith6StonesIn6Pits.postPlay();
        gameData.getTurnInfo().flipTurn();
        gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

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
        assertEquals(player1, gameData.getTurnInfo().whoseTurn());
    }

    @Test
    void playForPlayer2AndPit1() {

        PlayData playData = new PlayData(7, player2);
        GameData gameData = kalahaGameWith6StonesIn6Pits.postPlay();
        gameData.getTurnInfo().flipTurn();
        gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

        List<Pit> pits = gameData.getPits();
        assertEquals(6, pits.get(0).getStones());
        assertEquals(6, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(0, pits.get(7).getStones());
        assertEquals(7, pits.get(8).getStones());
        assertEquals(7, pits.get(9).getStones());
        assertEquals(7, pits.get(10).getStones());
        assertEquals(7, pits.get(11).getStones());
        assertEquals(7, pits.get(12).getStones());
        assertEquals(1, pits.get(13).getStones());
        assertEquals(13, gameData.getCurrentIndex());
        assertEquals(player2, gameData.getTurnInfo().whoseTurn());
    }

}