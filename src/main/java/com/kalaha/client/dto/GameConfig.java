package com.kalaha.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client side POJO that holds data regarding the configuration the game.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameConfig {

    /**
     * The name of the first player.
     */
    private String firstPlayersName;

    /**
     * The name of the second player.
     */
    private String secondPlayersName;

    /**
     * The number of pits per player.
     */
    private int numberOfPitsPerPlayer;

    /**
     * The number of stones per pit.
     */
    private int numberOfStonesPerPit;
}