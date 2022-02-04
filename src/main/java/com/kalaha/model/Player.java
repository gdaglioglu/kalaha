package com.kalaha.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a player and contains/manages the related data.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {

    /**
     * The id of the player.
     */
    @EqualsAndHashCode.Include
    private int id;


    /**
     * The name of the player.
     */
    private String name;
}