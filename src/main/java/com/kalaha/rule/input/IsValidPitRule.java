package com.kalaha.rule.input;

import com.kalaha.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rule implementation for checking a user can only play using their own pits that have stones.
 */
public class IsValidPitRule implements InputRule {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(IsValidPitRule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        PlayData playData = gameData.getPlayData();

        if (playData == null) {
            return;
        }

        int selectedPitIndex = playData.getSelectedPit();

        if (0 > selectedPitIndex || selectedPitIndex > gameData.getPits().size() - 1) {
            logger.error("Rule failure: {} for {}", Violation.NON_EXISTING_PIT.name(), playData);
            gameData.addViolation(Violation.NON_EXISTING_PIT);
            return;
        }

        Pit selectedPit = gameData.getPits().get(selectedPitIndex);

        if (selectedPit instanceof Kalaha) {
            logger.error("Rule failure: {} for {}", Violation.CANNOT_SELECT_KALAHA.name(), playData);
            gameData.addViolation(Violation.CANNOT_SELECT_KALAHA);
        }

        if (!selectedPit.getPlayer().equals(playData.getPlayer())) {
            logger.error("Rule failure: {} for {}", Violation.CANNOT_SELECT_OPPONENTS_PIT.name(), playData);
            gameData.addViolation(Violation.CANNOT_SELECT_OPPONENTS_PIT);
        }

        if (!(selectedPit instanceof Kalaha) &&
                selectedPit.getStones() == 0) {
            logger.error("Rule failure: {} for {}", Violation.NO_STONES_IN_PIT.name(), playData);
            gameData.addViolation(Violation.NO_STONES_IN_PIT);
        }

    }
}