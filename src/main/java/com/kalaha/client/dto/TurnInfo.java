package com.kalaha.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Client side POJO that holds turn information.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TurnInfo {

    /**
     * The player who is supposed to play.
     */
    private Player toPlay;
}