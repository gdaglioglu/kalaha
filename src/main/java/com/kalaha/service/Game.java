package com.kalaha.service;

import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;

/**
 * Blueprint for a game implementation. Utilises template pattern for logic execution.
 */
public abstract class Game {

    /**
     * Validates user input by specific rules per game type and updates game data.
     * @param playData the representation of selected pit by user.
     * @return <code>true</code> if no input violations detected, otherwise <code>false</code>.
     */
    protected abstract boolean isValidPlay(PlayData playData);

    /**
     * Executes the play logic and updates game data.
     * @param playData the representation of selected pit by user.
     */
    protected abstract void play(PlayData playData);

    /**
     * Runs post-play checks and updates game data as necessary.
     */
    protected abstract void postPlay();

    /**
     * Retrieves the game data.
     * @return  the reference of the game data.
     */
    protected abstract GameData getGameData();

    /**
     * Performs a play for a player. It first validates the input, then executes play logic,
     * finally performs post-play checks and retrieves game data.
     *
     * @param data the representation of selected pit by user.
     * @return the updated game data.
     */
    public final GameData playTurn(PlayData data) {

        if(isValidPlay(data)){
            play(data);
            postPlay();
        }

        return getGameData();
    }
}