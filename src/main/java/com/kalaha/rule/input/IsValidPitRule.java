package com.kalaha.rule.input;

import com.kalaha.model.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Rule implementation for checking a user can only play using their own pits that have stones.
 */
@Slf4j
public class IsValidPitRule implements InputRule {

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
            log.error("Rule failure: {} for {}", Violation.NON_EXISTING_PIT.name(), playData);
            gameData.addViolation(Violation.NON_EXISTING_PIT);
            return;
        }

        Pit selectedPit = gameData.getPits().get(selectedPitIndex);

        if (selectedPit instanceof Kalaha) {
            log.error("Rule failure: {} for {}", Violation.CANNOT_SELECT_KALAHA.name(), playData);
            gameData.addViolation(Violation.CANNOT_SELECT_KALAHA);
        }

        if (!selectedPit.getPlayer().equals(playData.getPlayer())) {
            log.error("Rule failure: {} for {}", Violation.CANNOT_SELECT_OPPONENTS_PIT.name(), playData);
            gameData.addViolation(Violation.CANNOT_SELECT_OPPONENTS_PIT);
        }

        if (!(selectedPit instanceof Kalaha) &&
                selectedPit.getStones() == 0) {
            log.error("Rule failure: {} for {}", Violation.NO_STONES_IN_PIT.name(), playData);
            gameData.addViolation(Violation.NO_STONES_IN_PIT);
        }

    }
}