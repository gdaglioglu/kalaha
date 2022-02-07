package com.kalaha.model;

import lombok.*;

/**
 * Represents a turn in the game and contains/manages the related data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayData {

    /**
     * The index of the selected pit by user.
     */
    private int selectedPit;


    /**
     * The user who selected the pit.
     */
    private Player player;
}
