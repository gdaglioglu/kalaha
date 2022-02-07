package com.kalaha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a turn in the game and contains/manages the related data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
