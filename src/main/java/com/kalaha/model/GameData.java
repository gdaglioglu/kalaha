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
    private List<Pit> pits;

    /**
     * The state of the game, e.g. whose turn, who won etc.
     */
    private TurnInfo turnInfo;

    private List<Violation> violationInfo;

    private GameInfo gameInfo;

    private PlayData playData;

    private Player firstPlayer;

    private Player secondPlayer;

    /**
     * The index of the pit where the last play finished.
     */
    private int currentIndex;

    public GameData(GameConfig gameConfig) {
        pits = new ArrayList<>();
        currentIndex = 0;
        firstPlayer = new Player(0, gameConfig.getPlayer1Name());
        secondPlayer = new Player(1, gameConfig.getPlayer2Name());
        turnInfo = new TurnInfo(firstPlayer,secondPlayer);
        violationInfo = new ArrayList<>();
        gameInfo = new GameInfo(firstPlayer, secondPlayer);
    }

    public boolean addViolation(Violation violation) {
        return violationInfo.add(violation);
    }

    public boolean hasRuleViolations() {
        return !violationInfo.isEmpty();
    }

    /**
     * Adds a pit to the list.
     *
     * @param pit The pit to be added.
     * @return <code>true</code> if succeeds, otherwise <code>false</code>.
     */
    public boolean addPit(Pit pit) {

        return pits.add(pit);
    }


    public void clearViolations() {
        violationInfo.clear();
    }
}