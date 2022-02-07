package com.kalaha.rule.play;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DeterminePlayerTurnRuleTest {

    private DeterminePlayerTurnRule determinePlayerTurnRule;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameData gameData;
    private PlayData playData;

    @BeforeEach
    void setUp() {
        determinePlayerTurnRule = new DeterminePlayerTurnRule();
        firstPlayer = new Player(0, "Test player 1");
        secondPlayer = new Player(1, "Test player 2");
        GameConfig gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());
        gameData = new GameData(gameConfig);
        playData = gameData.getPlayData();
    }

    @Test
    public void whenFirstPlayersLastStone_endsUpInFirstPlayersKahala_firstPlayersTurn() {

        gameData.setCurrentIndex(6);
        playData.setPlayer(firstPlayer);

        TurnInfo turnInfo = gameData.getTurnInfo();
        assertEquals(firstPlayer, turnInfo.whoseTurn());

        determinePlayerTurnRule.run(gameData);

        assertEquals(firstPlayer, turnInfo.whoseTurn());
    }

    @Test
    public void whenFirstPlayersLastStone_endsUpInFirstPlayersPit_secondPlayersTurn() {

        gameData.setCurrentIndex(5);
        playData.setPlayer(firstPlayer);

        TurnInfo turnInfo = gameData.getTurnInfo();
        assertEquals(firstPlayer, turnInfo.whoseTurn());

        determinePlayerTurnRule.run(gameData);

        assertEquals(secondPlayer, turnInfo.whoseTurn());
    }

    @Test
    public void whenFirstPlayersLastStone_endsUpInSecondPlayersPit_secondPlayersTurn() {

        gameData.setCurrentIndex(11);
        playData.setPlayer(firstPlayer);

        TurnInfo turnInfo = gameData.getTurnInfo();
        assertEquals(firstPlayer, turnInfo.whoseTurn());

        determinePlayerTurnRule.run(gameData);

        assertEquals(secondPlayer, turnInfo.whoseTurn());
    }

    @Test
    public void whenSecondPlayersLastStone_endsUpInSecondPlayersKahala_secondPlayersTurn() {

        gameData.setCurrentIndex(13);
        playData.setPlayer(secondPlayer);

        TurnInfo turnInfo = gameData.getTurnInfo();
        turnInfo.flipTurn();
        assertEquals(secondPlayer, turnInfo.whoseTurn());

        determinePlayerTurnRule.run(gameData);

        assertEquals(secondPlayer, turnInfo.whoseTurn());
    }

    @Test
    public void whenSecondPlayersLastStone_endsUpInSecondPlayersPit_firstPlayersTurn() {

        gameData.setCurrentIndex(9);
        playData.setPlayer(secondPlayer);

        TurnInfo turnInfo = gameData.getTurnInfo();
        turnInfo.flipTurn();
        assertEquals(secondPlayer, turnInfo.whoseTurn());

        determinePlayerTurnRule.run(gameData);

        assertEquals(firstPlayer, turnInfo.whoseTurn());
    }

    @Test
    public void whenSecondPlayersLastStone_endsUpInFirstPlayersPit_firstPlayersTurn() {

        gameData.setCurrentIndex(4);
        playData.setPlayer(secondPlayer);

        TurnInfo turnInfo = gameData.getTurnInfo();
        turnInfo.flipTurn();
        assertEquals(secondPlayer, turnInfo.whoseTurn());

        determinePlayerTurnRule.run(gameData);

        assertEquals(firstPlayer, turnInfo.whoseTurn());
    }

}