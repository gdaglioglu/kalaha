package com.kalaha.rule.play;

import com.kalaha.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Rule implementation to determine which user to play next.
 */
@Slf4j
public class DeterminePlayerTurnRule implements PlayRule {

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

        log.info("Turn decision: {}", turnInfo.getToPlay());
    }
}