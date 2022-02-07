package com.kalaha.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Client side POJO that holds data regarding the entire state of the game.
 */
@Getter
@Setter
@NoArgsConstructor
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
     * The state of the game, e.g. whose turn.
     */
    private TurnInfo turnInfo;

    /**
     * The list of violations, e.g. clicking on empty pit, clicking on opponents pits etc.
     */
    private List<Violation> violationInfo;

    /**
     * The game status, e.g. ongoing or won by one of the players.
     */
    private GameInfo gameInfo;

    /**
     * The first player.
     */
    private Player firstPlayer;

    /**
     * The second player.
     */
    private Player secondPlayer;
}