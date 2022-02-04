package com.kalaha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a pit on the board and contains/manages the related data i.e. stones, player.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pit {

    /**
     * The player who owns this pit.
     */
    private Player player;

    /**
     * The number of stones in this pit.
     */
    private int stones;

    public void addStone() {
        stones++;
    }
}