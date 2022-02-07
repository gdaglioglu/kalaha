package com.kalaha.model;

import lombok.Getter;

/**
 * TODO
 */
public class GameInfo {

    @Getter
    private GameStatus gameStatus;

    @Getter
    private Player winner;

    public GameInfo() {
        gameStatus = GameStatus.ONGOING;
    }

    public void establishWinner(Player player) {
        winner = player;
        gameStatus = GameStatus.FINISHED;
    }
}