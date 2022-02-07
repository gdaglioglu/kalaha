package com.kalaha.rule.play;

import com.kalaha.model.*;
import com.kalaha.rule.input.IsValidPitRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Rule implementation to determine which user to play next.
 */
public class DeterminePlayerTurnRule implements PlayRule {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(DeterminePlayerTurnRule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        List<Pit> pits = gameData.getPits();
        int lastPlayedIndex = gameData.getCurrentIndex();
        Pit lastPlayedPit = pits.get(lastPlayedIndex);

        PlayData playData = gameData.getPlayData();

        TurnInfo turnInfo = gameData.getTurnInfo();
        if (!(lastPlayedPit instanceof Kalaha &&
                lastPlayedPit.getPlayer().equals(playData.getPlayer()))) {

            turnInfo.flipTurn();
        }

        logger.info("Turn decision: {}", turnInfo.whoseTurn());
    }
}