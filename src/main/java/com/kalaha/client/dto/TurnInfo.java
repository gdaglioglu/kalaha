package com.kalaha.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client side POJO that holds turn information.
 */
@Getter
@Setter
@NoArgsConstructor
public class TurnInfo {

    /**
     * The player who is supposed to play.
     */
    private Player toPlay;
}