package com.kalaha.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
@NoArgsConstructor
public class GameInfo {

    @Getter
    private GameInfo.GameStatus gameStatus;

    @Getter
    private Player winner;

    public enum GameStatus {

        ONGOING,
        WIN_P1,
        WIN_P2;
    }

    private Map<Player, GameStatus> playerToGameStatusMap;

    public GameInfo(Player firstPlayer, Player secondPlayer) {
        gameStatus = GameStatus.ONGOING;
        playerToGameStatusMap = new HashMap<Player, GameStatus>();
        playerToGameStatusMap.put(firstPlayer, GameStatus.WIN_P1);
        playerToGameStatusMap.put(secondPlayer, GameStatus.WIN_P2);
    }

    public void establishWinner(Player player) {
        winner = player;
        gameStatus = playerToGameStatusMap.get(player);
    }


}