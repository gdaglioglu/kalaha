package com.kalaha.rule.input;

import com.kalaha.model.*;

/**
 * Rule implementation for checking a user can only play if it is their turn.
 */
public class IsPlayersTurnRule implements InputRule {

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        Player playerToPlay = gameData.getTurnInfo().whoseTurn();
        PlayData playData = gameData.getPlayData();

        if (playData != null && !playData.getPlayer().equals(playerToPlay)) {
            gameData.addViolation(Violation.NOT_PLAYERS_TURN);
        }
    }
}