package com.kalaha.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO that holds data regarding the state of the game.
 * TODO: Perhaps we can implement an interface to abstract game logic, i.e. playTurn - so it can be applied to other games than Kalaha
 */
@Getter
@Setter
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
        firstPlayer = new Player(0, gameConfig.getFirstPlayersName());
        secondPlayer = new Player(1, gameConfig.getSecondPlayersName());
        turnInfo = new TurnInfo(firstPlayer,secondPlayer);
        violationInfo = new ArrayList<>();
        gameInfo = new GameInfo();
        playData = new PlayData();

        for (int i = 0; i < gameConfig.getNumberOfPitsPerPlayer(); i++) {

            Pit pit = new Pit(firstPlayer, gameConfig.getNumberOfStonesPerPit());
            pits.add(pit);
        }
        Kalaha kalaha1 = new Kalaha(firstPlayer, 0);
        pits.add(kalaha1);

        for (int i = gameConfig.getNumberOfPitsPerPlayer() + 1; i < gameConfig.getNumberOfPitsPerPlayer() * 2 + 1; i++) {

            Pit pit = new Pit(secondPlayer, gameConfig.getNumberOfStonesPerPit());
            pits.add(pit);

        }
        Kalaha kalaha2 = new Kalaha(secondPlayer, 0);
        pits.add(kalaha2);
    }


    public boolean addViolation(Violation violation) {
        return violationInfo.add(violation);
    }

    public boolean hasRuleViolations() {
        return !violationInfo.isEmpty();
    }

    public void clearViolations() {
        violationInfo.clear();
    }
}