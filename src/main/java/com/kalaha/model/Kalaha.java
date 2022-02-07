package com.kalaha.model;

/**
 * Represents a Kalaha pit on the board and contains/manages the related data i.e. stones, player.
 */
public class Kalaha extends Pit {

    /**
     * Constructs a kalaha pit.
     *
     * @param player the player who owns this pit.
     * @param stones the number of stones in this pit.
     */
    public Kalaha(Player player, int stones) {
        super(player, stones);
    }
}