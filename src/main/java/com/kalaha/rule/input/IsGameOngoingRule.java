package com.kalaha.rule.input;

import com.kalaha.model.GameData;
import com.kalaha.model.GameStatus;
import com.kalaha.model.Violation;
import lombok.extern.slf4j.Slf4j;

/**
 * Rule implementation for checking a play can only be executed for games with status of {@link GameStatus#ONGOING}.
 */
@Slf4j
public class IsGameOngoingRule implements InputRule {

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        if(gameData.getGameInfo().getGameStatus() != GameStatus.ONGOING){
            log.error("Rule failure: {} for {}",Violation.GAME_ALREADY_OVER.name(), gameData.getPlayData());
            gameData.addViolation(Violation.GAME_ALREADY_OVER);
        }
    }
}
