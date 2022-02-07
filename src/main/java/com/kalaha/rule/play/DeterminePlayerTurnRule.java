package com.kalaha.rule.play;

import com.kalaha.model.*;

import java.util.List;

/**
 * Rule implementation to determine which user to play next.
 */
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

        if (!(lastPlayedPit instanceof Kalaha &&
                lastPlayedPit.getPlayer().equals(playData.getPlayer()))) {

            TurnInfo turnInfo = gameData.getTurnInfo();
            turnInfo.flipTurn();
        }
    }
}