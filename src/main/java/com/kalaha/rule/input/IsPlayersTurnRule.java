package com.kalaha.rule.input;

import com.kalaha.model.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Rule implementation for checking a user can only play if it is their turn.
 */
@Slf4j
public class IsPlayersTurnRule implements InputRule {

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        Player playerToPlay = gameData.getTurnInfo().getToPlay();
        PlayData playData = gameData.getPlayData();

        if (playData != null && !playData.getPlayer().equals(playerToPlay)) {
            log.error("Rule failure: {} for {}", Violation.NOT_PLAYERS_TURN.name(), playData);
            gameData.addViolation(Violation.NOT_PLAYERS_TURN);
        }
    }
}