package com.kalaha.service;

import com.kalaha.model.GameConfig;
import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import com.kalaha.rule.GameRule;
import lombok.Getter;

import java.util.List;

public abstract class Game {

    @Getter
    private final String firstPlayersName;

    @Getter
    private final String secondPlayersName;

    /**
     * Number of pits per player. This is configurable.
     */
    @Getter
    private final int numberOfPitsPerPlayer;

    /**
     * Number of stones per pit. This is configurable.
     */
    @Getter
    private final int numberOfStonesPerPit;

    @Getter
    private List<GameRule> gameRules;

    public Game(GameConfig gameConfig) {
        this.numberOfPitsPerPlayer = gameConfig.getNumberOfPitsPerPlayer();
        this.numberOfStonesPerPit = gameConfig.getNumberOfStonesPerPit();
        this.gameRules = gameConfig.getRules();
        this.firstPlayersName = gameConfig.getFirstPlayersName();
        this.secondPlayersName = gameConfig.getSecondPlayersName();
    }

    protected abstract void validate(PlayData playData);

    protected abstract void play(PlayData playData);

    protected abstract GameData getGameData();

    public GameData playTurn(PlayData playData) {
        validate(playData);
        play(playData);
        return getGameData();
    }

}