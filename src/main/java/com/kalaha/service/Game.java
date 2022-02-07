package com.kalaha.service;

import com.kalaha.model.GameConfig;
import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import com.kalaha.rule.GameRule;
import lombok.Getter;

import java.util.List;

/**
 * Blueprint to create a game. Utilises template pattern for logic execution.
 */
public abstract class Game {

    /**
     * The name of the first player.
     */
    @Getter
    private final String firstPlayersName;

    /**
     * The name of the second player.
     */
    @Getter
    private final String secondPlayersName;

    /**
     * Number of pits per player.
     */
    @Getter
    private final int numberOfPitsPerPlayer;

    /**
     * Number of stones per pit.
     */
    @Getter
    private final int numberOfStonesPerPit;

    /**
     * The list of rules configured for this game.
     */
    @Getter
    private final List<GameRule> gameRules;

    /**
     * Creates a game and maps common attributes for it.
     *
     * @param gameConfig the game config representation.
     */
    public Game(GameConfig gameConfig) {
        this.numberOfPitsPerPlayer = gameConfig.getNumberOfPitsPerPlayer();
        this.numberOfStonesPerPit = gameConfig.getNumberOfStonesPerPit();
        this.gameRules = gameConfig.getRules();
        this.firstPlayersName = gameConfig.getFirstPlayersName();
        this.secondPlayersName = gameConfig.getSecondPlayersName();
    }

    /**
     * Validates user input by specific rules per game type and updates game data.
     * @param playData the representation of selected pit by user.
     */
    protected abstract void prePlay(PlayData playData);

    /**
     * Executes the play logic and updates game data.
     * @param playData the representation of selected pit by user.
     */
    protected abstract void play(PlayData playData);

    /**
     * Runs post-play checks and updates game data as necessary..
     * @return  the reference of the game data.
     */
    protected abstract GameData postPlay();

    /**
     * Performs a play for a player. It first validates the input, then executes play logic,
     * finally performs post-play checks and retrieves game data.
     *
     * @param playData the representation of selected pit by user.
     * @return the updated game data.
     */
    public final GameData playTurn(PlayData playData) {
        prePlay(playData);
        play(playData);
        return postPlay();
    }
}