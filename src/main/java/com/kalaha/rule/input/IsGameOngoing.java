package com.kalaha.rule.input;

import com.kalaha.model.GameData;
import com.kalaha.model.GameInfo;
import com.kalaha.model.Violation;

public class IsGameOngoing implements InputRule {

    @Override
    public void run(GameData gameData) {

        if(gameData.getGameInfo().getGameStatus() != GameInfo.GameStatus.ONGOING){
            gameData.addViolation(Violation.GAME_ALREADY_OVER);
        }
    }
}
