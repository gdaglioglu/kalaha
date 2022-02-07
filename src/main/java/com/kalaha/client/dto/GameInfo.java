package com.kalaha.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Client side POJO that holds the game info, e.g. in progress game, wins etc.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GameInfo {

    /**
     * The game status.
     */
    private GameStatus gameStatus;

    /**
     * The player who won the game.
     */
    private Player winner;
}