package com.kalaha.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client side POJO that holds data about player.
 */
@Getter
@Setter
@NoArgsConstructor
public class Player {

    /**
     * The id of the player.
     */
    private int id;


    /**
     * The name of the player.
     */
    private String name;
}