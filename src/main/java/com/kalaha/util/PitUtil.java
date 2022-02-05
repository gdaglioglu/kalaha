package com.kalaha.util;

import com.kalaha.model.*;

import java.util.List;

public class PitUtil {

    public static Pit getOppositePit(GameData gameData) {
        List<Pit> pits = gameData.getPits();
        int indexOfOppositePit = (pits.size() - 2) - gameData.getCurrentIndex();
        return pits.get(indexOfOppositePit);
    }

    /**
     * TODO: Refactor this, possibly move to a util
     *
     * @param pit
     * @return
     */
    public static Pit getNextPit(GameData gameData, Pit pit) {

        List<Pit> pits = gameData.getPits();
        int indexSelectedPit = pits.indexOf(pit);

        PlayData playData = new PlayData();
        playData.setSelectedPit(indexSelectedPit);
        playData.setPlayer(pit.getPlayer());

        return getNextPit(gameData,playData);
    }

    /**
     * TODO: Refactor this, possibly move to a util
     *
     * @param playData
     * @return
     */
    public static Pit getNextPit(GameData gameData, PlayData playData) {

        List<Pit> pits = gameData.getPits();
        Player player = playData.getPlayer();
        int indexSelectedPit = playData.getSelectedPit();

        int nextIndex = indexSelectedPit + 1;
        if (nextIndex >= pits.size()) {
            nextIndex = 0;
        }

        Pit proposedPit = pits.get(nextIndex);
        if (proposedPit instanceof Kalaha && !proposedPit.getPlayer().equals(player)) {
            PlayData newData = new PlayData();
            newData.setSelectedPit(nextIndex + 1);
            newData.setPlayer(playData.getPlayer());

            return getNextPit(gameData,newData);
        } else {
            gameData.setCurrentIndex(pits.indexOf(proposedPit));
            return proposedPit;
        }
    }
}
