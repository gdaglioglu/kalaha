package com.kalaha.model;

import lombok.Getter;
import lombok.ToString;

/**
 * POJO that holds game status and winner player information.
 */
@ToString
public class GameInfo {

    @Getter
    private GameStatus gameStatus;

    @Getter
    private Player winner;

    public GameInfo() {
        gameStatus = GameStatus.ONGOING;
    }

    public void setWinner(Player player) {
        winner = player;
        gameStatus = GameStatus.FINISHED;
    }
}