package com.kalaha.rule.play;

import com.kalaha.model.GameData;
import com.kalaha.model.Pit;
import com.kalaha.model.PlayData;
import com.kalaha.util.PitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Rule implementation to distribute stones to appropriate pits.
 */
public class DistributeStonesRule implements PlayRule {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(DistributeStonesRule.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        PlayData playData = gameData.getPlayData();
        int indexSelectedPit = playData.getSelectedPit();

        List<Pit> pits = gameData.getPits();
        Pit selectedPit = pits.get(indexSelectedPit);
        int numberOfStones = selectedPit.getStones();
        selectedPit.setStones(0);

        while (numberOfStones > 0) {
            selectedPit = PitUtil.getNextPit(gameData, selectedPit);
            selectedPit.setStones(selectedPit.getStones() + 1);
            numberOfStones--;
        }

        logger.info("Play completed for {}", playData);
    }
}