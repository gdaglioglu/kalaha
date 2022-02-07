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

/**
 * Tests functionality exposed in {@link KalahaGame} class.
 */
class KalahaGameTest {

    private KalahaGame kalahaGameWith6StonesIn6Pits;
    private Player firstPlayer;
    private Player secondPlayer;

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

        GameConfig gameConfig = new GameConfig("Player 1 name", "Player 2 name", 6, 6, rules);
        kalahaGameWith6StonesIn6Pits = new KalahaGame(gameConfig);

        firstPlayer = new Player(0, "Player 1 name");
        secondPlayer = new Player(1, "Player 2 name");
    }

    @Test
    void newGameCreatedWithDefaults() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.getGameData();
        assertNotNull(returnedGameData, "Game creation failed");
        assertEquals(0, returnedGameData.getCurrentIndex());
        GameInfo returnedGameInfo = returnedGameData.getGameInfo();
        assertEquals(GameStatus.ONGOING, returnedGameInfo.getGameStatus());
        assertNull(returnedGameInfo.getWinner(), "No winner should have been established yet!");
        assertEquals(firstPlayer, returnedGameData.getFirstPlayer());
        assertEquals(secondPlayer, returnedGameData.getSecondPlayer());
        assertEquals(firstPlayer, returnedGameData.getTurnInfo().getToPlay());
        assertTrue(returnedGameData.getViolationInfo().isEmpty(), "No violations reported yet!");
        PlayData returnedPlayData = returnedGameData.getPlayData();
        assertNull(returnedPlayData.getPlayer(), "No play data exists yet!");
        assertEquals(0, returnedPlayData.getSelectedPit());
    }

    @Test
    void newGameCreatedWithCorrectPitStructure() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.getGameData();
        List<Pit> returnedPits = returnedGameData.getPits();

        assertEquals(14, returnedPits.size());

        List<Pit> pitList = returnedPits.stream().filter(pit -> !(pit instanceof Kalaha)).collect(Collectors.toList());

        assertTrue(returnedPits.get(returnedPits.size() - 1) instanceof Kalaha, "Kalaha not found in expected index");
        assertTrue(returnedPits.get(returnedPits.size() / 2 - 1) instanceof Kalaha, "Kalaha not found in expected index");
        assertEquals(returnedPits.size() - 2, pitList.size());
    }

    @Test
    void newGameCreatedWithCorrectKalahaData() {

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.getGameData();
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

        GameData returnedGameData = kalahaGameWith6StonesIn6Pits.getGameData();
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
    void whenFirstPlayer_playTurn_forPit0_playCompletes() {

        PlayData playData = new PlayData(0, firstPlayer);
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
        assertEquals(firstPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(0, gameData.getViolationInfo().size());
        assertEquals(GameStatus.ONGOING, gameData.getGameInfo().getGameStatus());
        assertNull(gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }

    @Test
    void whenFirstPlayer_playTurn_forPit5_playCompletes() {

        PlayData playData = new PlayData(5, firstPlayer);
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
        assertEquals(secondPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(0, gameData.getViolationInfo().size());
        assertEquals(GameStatus.ONGOING, gameData.getGameInfo().getGameStatus());
        assertNull(gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }

    @Test
    void whenSecondPlayer_playTurn_ForPit8_playCompletes() {

        PlayData playData = new PlayData(8, secondPlayer);
        GameData gameData = kalahaGameWith6StonesIn6Pits.getGameData();
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
        assertEquals(firstPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(0, gameData.getViolationInfo().size());
        assertEquals(GameStatus.ONGOING, gameData.getGameInfo().getGameStatus());
        assertNull(gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }

    @Test
    void whenSecondPlayer_playTurn_ForPit7_playCompletes() {

        PlayData playData = new PlayData(7, secondPlayer);
        GameData gameData = kalahaGameWith6StonesIn6Pits.getGameData();
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
        assertEquals(secondPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(0, gameData.getViolationInfo().size());
        assertEquals(GameStatus.ONGOING, gameData.getGameInfo().getGameStatus());
        assertNull(gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }

    @Test
    void whenFirstPlayer_playTurn_ForPit10_invalidPitViolation() {

        PlayData playData = new PlayData(10, firstPlayer);
        GameData gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

        List<Pit> pits = gameData.getPits();
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
        assertEquals(0, pits.get(13).getStones());
        assertEquals(0, gameData.getCurrentIndex());
        assertEquals(firstPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.CANNOT_SELECT_OPPONENTS_PIT, gameData.getViolationInfo().get(0));
        assertEquals(GameStatus.ONGOING, gameData.getGameInfo().getGameStatus());
        assertNull(gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }

    @Test
    void whenSecondPlayer_playTurn_ForPit10_invalidTurnViolation() {

        PlayData playData = new PlayData(10, secondPlayer);
        GameData gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

        List<Pit> pits = gameData.getPits();
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
        assertEquals(0, pits.get(13).getStones());
        assertEquals(0, gameData.getCurrentIndex());
        assertEquals(firstPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.NOT_PLAYERS_TURN, gameData.getViolationInfo().get(0));
        assertEquals(GameStatus.ONGOING, gameData.getGameInfo().getGameStatus());
        assertNull(gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }

    @Test
    void whenFirstPlayer_playTurn_ForPit5_gameFinishedViolation() {

        PlayData playData = new PlayData(5, firstPlayer);
        kalahaGameWith6StonesIn6Pits.getGameData().getGameInfo().setWinner(secondPlayer);
        GameData gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

        List<Pit> pits = gameData.getPits();
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
        assertEquals(0, pits.get(13).getStones());
        assertEquals(0, gameData.getCurrentIndex());
        assertEquals(firstPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(1, gameData.getViolationInfo().size());
        assertEquals(Violation.GAME_ALREADY_OVER, gameData.getViolationInfo().get(0));
        assertEquals(GameStatus.FINISHED, gameData.getGameInfo().getGameStatus());
        assertEquals(secondPlayer, gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }

    @Test
    void whenSecondPlayer_playTurn_ForPit10_collectStonesFromOppositePit() {

        PlayData playData = new PlayData(10, secondPlayer);
        kalahaGameWith6StonesIn6Pits.getGameData().getTurnInfo().flipTurn();
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(10).setStones(1);
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(11).setStones(0);

        GameData gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

        List<Pit> pits = gameData.getPits();
        assertEquals(6, pits.get(0).getStones());
        assertEquals(0, pits.get(1).getStones());
        assertEquals(6, pits.get(2).getStones());
        assertEquals(6, pits.get(3).getStones());
        assertEquals(6, pits.get(4).getStones());
        assertEquals(6, pits.get(5).getStones());
        assertEquals(0, pits.get(6).getStones());
        assertEquals(6, pits.get(7).getStones());
        assertEquals(6, pits.get(8).getStones());
        assertEquals(6, pits.get(9).getStones());
        assertEquals(0, pits.get(10).getStones());
        assertEquals(0, pits.get(11).getStones());
        assertEquals(6, pits.get(12).getStones());
        assertEquals(7, pits.get(13).getStones());
        assertEquals(13, gameData.getCurrentIndex());
        assertEquals(secondPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(0, gameData.getViolationInfo().size());
        assertEquals(GameStatus.ONGOING, gameData.getGameInfo().getGameStatus());
        assertNull(gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }


    @Test
    void whenFirstPlayer_playTurn_ForPit5_gameFinishes() {

        PlayData playData = new PlayData(5, firstPlayer);
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(0).setStones(0);
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(1).setStones(0);
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(2).setStones(0);
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(3).setStones(0);
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(4).setStones(0);
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(5).setStones(1);
        kalahaGameWith6StonesIn6Pits.getGameData().getPits().get(6).setStones(37);

        GameData gameData = kalahaGameWith6StonesIn6Pits.playTurn(playData);

        List<Pit> pits = gameData.getPits();
        assertEquals(0, pits.get(0).getStones());
        assertEquals(0, pits.get(1).getStones());
        assertEquals(0, pits.get(2).getStones());
        assertEquals(0, pits.get(3).getStones());
        assertEquals(0, pits.get(4).getStones());
        assertEquals(0, pits.get(5).getStones());
        assertEquals(38, pits.get(6).getStones());
        assertEquals(0, pits.get(7).getStones());
        assertEquals(0, pits.get(8).getStones());
        assertEquals(0, pits.get(9).getStones());
        assertEquals(0, pits.get(10).getStones());
        assertEquals(0, pits.get(11).getStones());
        assertEquals(0, pits.get(12).getStones());
        assertEquals(36, pits.get(13).getStones());
        assertEquals(6, gameData.getCurrentIndex());
        assertEquals(firstPlayer, gameData.getTurnInfo().getToPlay());
        assertEquals(0, gameData.getViolationInfo().size());
        assertEquals(GameStatus.FINISHED, gameData.getGameInfo().getGameStatus());
        assertEquals(firstPlayer, gameData.getGameInfo().getWinner());
        assertEquals(playData, gameData.getPlayData());
    }
}