package com.kalaha.model;

import com.kalaha.rule.GameRule;
import lombok.*;

import java.util.List;

/**
 * POJO that holds game configuration information
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
     * The number of the pits per player.
     */
    private int numberOfPitsPerPlayer;

    /**
     * The number of the stones per pit.
     */
    private int numberOfStonesPerPit;

    /**
     * The list of game rules to be run.
     */
    private List<GameRule> rules;
}
