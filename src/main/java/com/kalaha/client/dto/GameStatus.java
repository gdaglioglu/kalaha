package com.kalaha.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Client side POJO that holds the game status, e.g. in progress or finished game.
 */
@Getter
@NoArgsConstructor
public enum GameStatus {
    ONGOING,
    FINISHED
}
