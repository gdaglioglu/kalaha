package com.kalaha.rule.input;

import com.kalaha.model.*;

public class IsValidPit implements InputRule {

    @Override
    public void run(GameData gameData) {

        PlayData playData = gameData.getPlayData();

        if(playData == null){
            return;
        }

        int selectedPitIndex = playData.getSelectedPit();
        Pit selectedPit = gameData.getPits().get(selectedPitIndex);

        if(selectedPit == null){
            gameData.addViolation(Violation.NON_EXISTING_PIT);
            return;
        }

        if(selectedPit instanceof Kalaha){
            gameData.addViolation(Violation.CANNOT_SELECT_KALAHA);
        }

        if(!selectedPit.getPlayer().equals(playData.getPlayer())){
            gameData.addViolation(Violation.CANNOT_SELECT_OPPONENTS_PIT);
        }

        if(selectedPit.getStones() == 0){
            gameData.addViolation(Violation.NO_STONES_IN_PIT);
        }

    }
}