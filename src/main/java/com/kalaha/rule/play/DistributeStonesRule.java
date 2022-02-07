package com.kalaha.rule.play;

import com.kalaha.model.GameData;
import com.kalaha.model.Pit;
import com.kalaha.model.PlayData;
import com.kalaha.util.PitUtil;

import java.util.List;

/**
 * Rule implementation to distribute stones to appropriate pits.
 */
public class DistributeStonesRule implements PlayRule {

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
    }
}