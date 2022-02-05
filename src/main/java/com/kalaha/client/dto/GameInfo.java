package com.kalaha.client.dto;

import com.kalaha.model.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client side POJO that holds the game status info, e.g. in progress game, wins etc.
 */
@Getter
@Setter
@NoArgsConstructor
public class GameInfo {

    private GameInfo.GameStatus gameStatus;

    private Player winner;

    public enum GameStatus {

        ONGOING,
        WIN_P1,
        WIN_P2;
    }
}