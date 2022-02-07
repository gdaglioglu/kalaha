package com.kalaha.rule.input;

import com.kalaha.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rule implementation for checking a user can only play if it is their turn.
 */
public class IsPlayersTurnRule implements InputRule {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(IsPlayersTurnRule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        Player playerToPlay = gameData.getTurnInfo().whoseTurn();
        PlayData playData = gameData.getPlayData();

        if (playData != null && !playData.getPlayer().equals(playerToPlay)) {
            logger.error("Rule failure: {} for {}", Violation.NOT_PLAYERS_TURN.name(), playData);
            gameData.addViolation(Violation.NOT_PLAYERS_TURN);
        }
    }
}