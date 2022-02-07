package com.kalaha.rule;

import com.kalaha.model.GameData;

/**
 * Interface to be implemented to create a new game rule.
 */
public interface GameRule {

    /**
     * Runs rule specific checks and updates game data accordingly.
     * @param gameData the reference to the game data.
     */
     void run(GameData gameData);
}