package com.kalaha.rule.input;

import com.kalaha.model.*;

public class IsValidPitRule implements InputRule {

    @Override
    public void run(GameData gameData) {

        PlayData playData = gameData.getPlayData();

        if (playData == null) {
            return;
        }

        int selectedPitIndex = playData.getSelectedPit();

        if (0 > selectedPitIndex || selectedPitIndex > gameData.getPits().size() - 1) {
            gameData.addViolation(Violation.NON_EXISTING_PIT);
            return;
        }

        Pit selectedPit = gameData.getPits().get(selectedPitIndex);

        if (selectedPit instanceof Kalaha) {
            gameData.addViolation(Violation.CANNOT_SELECT_KALAHA);
        }

        if (!selectedPit.getPlayer().equals(playData.getPlayer())) {
            gameData.addViolation(Violation.CANNOT_SELECT_OPPONENTS_PIT);
        }

        if (!(selectedPit instanceof Kalaha) &&
                selectedPit.getStones() == 0) {
            gameData.addViolation(Violation.NO_STONES_IN_PIT);
        }

    }
}