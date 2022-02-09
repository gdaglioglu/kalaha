package com.kalaha.rule.play;

import com.kalaha.model.*;
import com.kalaha.util.PitUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Rule implementation for a user to collect opponent's stones and move them to their kalaha.
 */
@Slf4j
public class CollectStonesFromOppositePitRule implements PlayRule {

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        List<Pit> pits = gameData.getPits();
        Pit lastPlayedPit = pits.get(gameData.getCurrentIndex());
        PlayData playData = gameData.getPlayData();

        if (lastPlayedPit != null &&
                !(lastPlayedPit instanceof Kalaha) &&
                lastPlayedPit.getPlayer().equals(playData.getPlayer()) &&
                lastPlayedPit.getStones() == 1) {

            Pit oppositePit = PitUtil.getOppositePit(gameData);
            int stoneCount = oppositePit.getStones();
            stoneCount += lastPlayedPit.getStones();
            oppositePit.setStones(0);
            lastPlayedPit.setStones(0);

            Pit playersKalaha = pits.stream().
                    filter(pit -> pit instanceof Kalaha && pit.getPlayer().equals(playData.getPlayer())).
                    findFirst().get();
            playersKalaha.setStones(playersKalaha.getStones() + stoneCount);
            gameData.setCurrentIndex(pits.indexOf(playersKalaha));

            log.info("Stones collected from opposite pit for {}", playData);
        }


    }
}