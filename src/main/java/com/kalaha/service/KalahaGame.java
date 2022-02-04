package com.kalaha.service;

import com.kalaha.model.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * TODO: Add an abstract Game class to implement Template pattern.
 * Game rules and win rules are to be incorporated. Just a very basic scenario works for now.
 * Holds the specific Kalaha game logic.
 */
@Getter
public class KalahaGame {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(KalahaGame.class);

    /**
     * Number of pits per player. This is configurable.
     */
    private final int numberOfPitsPerPlayer;

    /**
     * Number of stones per pit. This is configurable.
     */
    private final int numberOfStonesPerPit;

    /**
     * Any specific data about the game.
     */
    private final GameData gameData;

    /**
     * Constructs a basic Kalaha game.
     *
     * @param numberOfPitsPerPlayer number of pits per player.
     * @param numberOfStonesPerPit  number of stones per pit.
     */
    protected KalahaGame(int numberOfPitsPerPlayer, int numberOfStonesPerPit) {

        this.numberOfPitsPerPlayer = numberOfPitsPerPlayer;
        this.numberOfStonesPerPit = numberOfStonesPerPit;
        this.gameData = new GameData();
    }


    /**
     * TODO: Refactor this.
     * Initializes a new game.
     * @return the game data.
     */
    public GameData newGame() {

        Player player1 = new Player(0, "Player 1");
        Player player2 = new Player(1, "Player 2");

        for (int i = 0; i < numberOfPitsPerPlayer; i++) {

            Pit pit = new Pit(player1, numberOfStonesPerPit);
            gameData.addPit(pit);
        }
        Kalaha kalaha1 = new Kalaha(player1, 0);
        gameData.addPit(kalaha1);

        for (int i = numberOfPitsPerPlayer + 1; i < numberOfPitsPerPlayer * 2 + 1; i++) {

            Pit pit = new Pit(player2, numberOfStonesPerPit);
            gameData.addPit(pit);
        }
        Kalaha kalaha2 = new Kalaha(player2, 0);
        gameData.addPit(kalaha2);

        return this.gameData;
    }

    /**
     * TODO: Nowhere near complete. Only a basic scenario works.
     * @param playData the turn data.
     * @return the updated game data after turn.
     */
    public GameData play(PlayData playData) {

        // Pre-checks: Is it the turn of the player who is supposed to play?
        // Selected an actual pit that exists, and belongs to them
        // selected pit is not empty

        int indexSelectedPit = playData.getSelectedPit();

        List<Pit> pits = gameData.getPits();
        Pit selectedPit = pits.get(indexSelectedPit);
        int numberOfStones = selectedPit.getStones();
        selectedPit.setStones(0);

        Pit nextPit = gameData.getNextPit(playData);
        while (numberOfStones > 0) {
            nextPit.addStone();
            nextPit = gameData.getNextPit(nextPit);
            numberOfStones--;
        }


        //post checks:
        // Win, if there are no stones left in your own pits(except your kalaha), game over. We need to count kalaha to decide winner.
        // if last stone ends in an empty pit, collect the contents of opposite pit and move all to your kalaha
        // if last stone ends in your kalaha, it's your turn again.
        GameState gameState = gameData.getGameState() == GameState.TURN_P1 ? GameState.TURN_P2 : GameState.TURN_P1;
        gameData.setGameState(gameState);

        return gameData;
    }
}
