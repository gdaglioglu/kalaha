package com.kalaha.util;

import com.kalaha.model.*;

import java.util.List;

public class PitUtil {

    /**
     * TODO:
     */
    public static Pit getNextPit(GameData gameData, Pit pit) {

        List<Pit> pits = gameData.getPits();
        int indexOfCurrentPit = pits.indexOf(pit);

        int indexOfProposedPit = indexOfCurrentPit + 1;
        if (indexOfProposedPit >= pits.size()) {
            indexOfProposedPit = 0;
        }

        Pit proposedPit = pits.get(indexOfProposedPit);
        PlayData playData = gameData.getPlayData();
        Player playerPlayed = playData.getPlayer();

        if (proposedPit instanceof Kalaha && !proposedPit.getPlayer().equals(playerPlayed)) {

            return getNextPit(gameData, proposedPit);
        } else {
            gameData.setCurrentIndex(indexOfProposedPit);
            return proposedPit;
        }
    }

    // TODO: check how can we control if given pit is kalaha, error case
    public static Pit getOppositePit(GameData gameData) {

        List<Pit> pits = gameData.getPits();
        int currentIndex = gameData.getCurrentIndex();
        int indexOfOppositePit = (pits.size() - 2) - currentIndex;
        return pits.get(indexOfOppositePit);
    }


}
