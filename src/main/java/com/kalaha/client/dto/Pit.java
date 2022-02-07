package com.kalaha.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Client side POJO that holds data for pits on the board i.e. stones, player.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pit {

    /**
     * The player who owns this pit.
     */
    private Player player;

    /**
     * The number of stones in this pit.
     */
    private int stones;
}