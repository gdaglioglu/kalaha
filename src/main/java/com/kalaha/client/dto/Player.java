package com.kalaha.client.dto;

import lombok.*;

/**
 * Client side POJO that holds data about player.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
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