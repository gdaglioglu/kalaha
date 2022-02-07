package com.kalaha.rule.input;

import com.kalaha.model.GameData;
import com.kalaha.model.GameStatus;
import com.kalaha.model.Violation;

/**
 * Rule implementation for checking a play can only be executed for games with status of {@link GameStatus#ONGOING}.
 */
public class IsGameOngoingRule implements InputRule {

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        if(gameData.getGameInfo().getGameStatus() != GameStatus.ONGOING){
            gameData.addViolation(Violation.GAME_ALREADY_OVER);
        }
    }
}
