package com.kalaha.rule.input;

import com.kalaha.model.GameData;
import com.kalaha.model.GameStatus;
import com.kalaha.model.Violation;
import com.kalaha.service.KalahaGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rule implementation for checking a play can only be executed for games with status of {@link GameStatus#ONGOING}.
 */
public class IsGameOngoingRule implements InputRule {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(IsGameOngoingRule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        if(gameData.getGameInfo().getGameStatus() != GameStatus.ONGOING){
            logger.error("Rule failure: {} for {}",Violation.GAME_ALREADY_OVER.name(), gameData.getPlayData());
            gameData.addViolation(Violation.GAME_ALREADY_OVER);
        }
    }
}
