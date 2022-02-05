package com.kalaha.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameConfig {

    private String player1Name;

    private String player2Name;

    private int numberOfPitsPerPlayer;

    private int numberOfStonesPerPit;
}
