package com.kalaha.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO that holds data regarding the state of the game.
 * TODO: Perhaps we can implement an interface to abstract game logic, i.e. playTurn - so it can be applied to other games than Kalaha
 */
@Getter
@Setter
@NoArgsConstructor
public class GameData {

    /**
     * The collection of pits on board.
     */
    private List<Pit> pits = new ArrayList<>();

    /**
     * The state of the game, e.g. whose turn, who won etc.
     */
    private GameState gameState = GameState.TURN_P1;


    /**
     * The index of the pit where the last play finished.
     */
    private int currentIndex = 0;

    /**
     * Adds a pit to the list.
     * @param pit The pit to be added.
     * @return <code>true</code> if succeeds, otherwise <code>false</code>.
     */
    public boolean addPit(Pit pit) {

        return pits.add(pit);
    }


    /**
     * TODO: Refactor this, possibly move to a util
     * @param playData
     * @return
     */
    public Pit getNextPit(PlayData playData) {
        int playerId = playData.getPlayerID();
        int indexSelectedPit = playData.getSelectedPit();

        int nextIndex = indexSelectedPit + 1;
        if (nextIndex >= pits.size()) {
            nextIndex = 0;
        }

        Pit proposedPit = pits.get(nextIndex);
        if (proposedPit instanceof Kalaha && proposedPit.getPlayer().getId() != playerId) {
            PlayData newData = new PlayData();
            newData.setSelectedPit(nextIndex + 1);
            newData.setPlayerID(playData.getPlayerID());

            return getNextPit(newData);
        } else {
            currentIndex = pits.indexOf(proposedPit) - 1;
            return proposedPit;
        }
    }

    /**
     * TODO: Refactor this, possibly move to a util
     * @param pit
     * @return
     */
    public Pit getNextPit(Pit pit) {
        int indexSelectedPit = pits.indexOf(pit);

        PlayData playData = new PlayData();
        playData.setSelectedPit(indexSelectedPit);
        playData.setPlayerID(pit.getPlayer().getId());

        return getNextPit(playData);
    }
}