package com.kalaha.model;

import lombok.*;

/**
 * Represents a player and contains/manages the related data.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
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