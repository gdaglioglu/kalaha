package com.kalaha.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Holds turn information in the game.
 */
@NoArgsConstructor
@ToString
public class TurnInfo {

    @Getter
    private Player toPlay;
    private Player firstPlayer;
    private Player secondPlayer;

    /**
     * Constructs a turn info.
     * @param firstPlayer the reference to the first player.
     * @param secondPlayer the reference to the second player.
     */
    public TurnInfo(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.toPlay = firstPlayer;
    }

    /**
     * Flips the turn to the other player.
     */
    public void flipTurn() {

        if (firstPlayer.equals(toPlay)) {
            toPlay = secondPlayer;
        } else if (secondPlayer.equals(toPlay)) {
            toPlay = firstPlayer;
        }
    }
}