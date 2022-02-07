package com.kalaha.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents game states like turns and wins.
 */
@NoArgsConstructor
@Getter
public class TurnInfo {

    private Player toPlay;
    private Player firstPlayer;
    private Player secondPlayer;

    public TurnInfo(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.toPlay = firstPlayer;
    }

    public void flipTurn() {

        if (firstPlayer.equals(toPlay)) {
            toPlay = secondPlayer;
        } else if (secondPlayer.equals(toPlay)) {
            toPlay = firstPlayer;
        }
    }

    public Player whoseTurn() {
        return toPlay;
    }
}