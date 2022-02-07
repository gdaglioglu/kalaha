package com.kalaha.rule.input;

import com.kalaha.model.GameData;
import com.kalaha.model.GameStatus;
import com.kalaha.model.Violation;

public class IsGameOngoingRule implements InputRule {

    @Override
    public void run(GameData gameData) {

        if(gameData.getGameInfo().getGameStatus() != GameStatus.ONGOING){
            gameData.addViolation(Violation.GAME_ALREADY_OVER);
        }
    }
}
