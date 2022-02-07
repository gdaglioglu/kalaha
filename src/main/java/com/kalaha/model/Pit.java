package com.kalaha.model;

import lombok.*;

/**
 * Represents a pit on the board and contains/manages the related data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pit {

    /**
     * The player who owns this pit.
     */
    private Player player;

    /**
     * The number of stones in this pit.
     */
    private int stones;
}