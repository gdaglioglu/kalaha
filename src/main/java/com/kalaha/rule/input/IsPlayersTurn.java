package com.kalaha.rule.input;

import com.kalaha.model.*;

public class IsPlayersTurn implements InputRule {

    @Override
    public void run(GameData gameData) {

        TurnInfo turnInfo = gameData.getTurnInfo();
        Player playerToPlay = turnInfo.whoseTurn();
        PlayData playData = gameData.getPlayData();

        if (playData != null && !playData.getPlayer().equals(playerToPlay)) {
            gameData.addViolation(Violation.NOT_PLAYERS_TURN);
        }
    }
}
