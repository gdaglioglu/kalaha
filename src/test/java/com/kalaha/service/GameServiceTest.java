package com.kalaha.service;

import com.kalaha.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * Tests functionality exposed in {@link GameService} class.
 */
@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameFactory mockGameFactory;

    @Mock
    private GameData mockGameData;

    @Mock
    private KalahaGame mockGame;

    @InjectMocks
    private GameService gameService;

    private final Player firstPlayer = new Player(0, "Player 1 name");
    private final Player secondPlayer = new Player(0, "Player 2 name");
    private final GameConfig gameConfig = new GameConfig(firstPlayer.getName(), secondPlayer.getName(), 6, 6, Collections.emptyList());

    @BeforeEach
    public void setUp() {
        lenient().when(mockGameFactory.createGame(gameConfig)).thenReturn(mockGame);
        lenient().when(mockGame.getGameData()).thenReturn(mockGameData);
    }

    @Test
    void newGameCreated_methodExecutionsVerified() {

        GameData returnedGameData = gameService.newGame(gameConfig);
        assertEquals(mockGameData, returnedGameData);
    }

    @Test
    void playInvoked_methodExecutionsVerified() {

        PlayData playData = new PlayData(0, firstPlayer);
        when(mockGame.playTurn(playData)).thenReturn(mockGameData);

        gameService.newGame(gameConfig);
        GameData returnedGameData = gameService.play(playData);
        assertEquals(mockGameData, returnedGameData);
    }

    @Test
    void getGame_gameIsNotInitialized() {

        GameData returnedGameData = gameService.getGame(1L);
        assertNull(returnedGameData);
    }

    @Test
    void getGame_gameIsInitialized() {

        gameService.newGame(gameConfig);
        GameData returnedGameData = gameService.getGame(1L);
        assertEquals(mockGameData, returnedGameData);
    }
}