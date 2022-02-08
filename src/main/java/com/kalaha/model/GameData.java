package com.kalaha.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the entire data/information regarding the state of the game.
 */
@Getter
@Setter
@ToString
public class GameData {

    /**
     * The id of the game data.
     */
    private Long id;

    /**
     * The collection of pits on board.
     */
    private List<Pit> pits;

    /**
     * The turn information for the game.
     */
    private TurnInfo turnInfo;

    /**
     * The collection of violations for the last played turn.
     */
    private List<Violation> violationInfo;

    /**
     * The game information, e.g. ongoing vs. finished, who won etc.
     */
    private GameInfo gameInfo;

    /**
     * The last play information for the game.
     */
    private PlayData playData;

    /**
     * The first player reference.
     */
    private Player firstPlayer;

    /**
     * The second player reference.
     */
    private Player secondPlayer;

    /**
     * The index of the pit where the last play finished.
     */
    private int currentIndex;

    /**
     * Constructs game data based on specified configuration.
     *
     * @param gameConfig The configuration to be used.
     */
    public GameData(GameConfig gameConfig) {

        // game id and player ids should of course be generated in the future.
        id = 1L;
        pits = new ArrayList<>();
        currentIndex = 0;
        firstPlayer = new Player(0, gameConfig.getFirstPlayersName());
        secondPlayer = new Player(1, gameConfig.getSecondPlayersName());
        turnInfo = new TurnInfo(firstPlayer, secondPlayer);
        violationInfo = new ArrayList<>();
        gameInfo = new GameInfo();
        playData = new PlayData();

        int pitSize = gameConfig.getNumberOfPitsPerPlayer() * 2 + 2;
        int firstPlayersKalahaIndex = (pitSize - 2) / 2;
        int secondPlayersKalahaIndex = pitSize - 1;
        int numberOfStonesPerPit=gameConfig.getNumberOfStonesPerPit();

        for (int i = 0; i < pitSize; i++) {

            Pit pit = null;
            if(i < firstPlayersKalahaIndex){
                 pit = new Pit(firstPlayer, numberOfStonesPerPit);
            }else if(i == firstPlayersKalahaIndex){
                 pit = new Kalaha(firstPlayer, 0);
            }else if(i<secondPlayersKalahaIndex){
                pit = new Pit(secondPlayer, numberOfStonesPerPit);
            }else if(i == secondPlayersKalahaIndex){
                 pit = new Kalaha(secondPlayer, 0);
            }
            pits.add(pit);
        }
    }

    /**
     * Adds the specified violation to the violation collection.
     *
     * @param violation The item be added.
     */
    public void addViolation(Violation violation) {
        violationInfo.add(violation);
    }

    /**
     * Checks whether there are violation in the collection.
     *
     * @return <code>true</code> if violations exist, otherwise <code>false</code>.
     */
    public boolean hasRuleViolations() {
        return !violationInfo.isEmpty();
    }

    /**
     * Clears all violations from the collection.
     */
    public void clearViolations() {
        violationInfo.clear();
    }
}