package com.kalaha.model;

import com.kalaha.rule.GameRule;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameConfig {

    private String player1Name;

    private String player2Name;

    private int numberOfPitsPerPlayer;

    private int numberOfStonesPerPit;

    private List<GameRule> rules;
}
