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

    private TurnStatus turnStatus;

    public enum TurnStatus {
        TURN_P1,
        TURN_P2
    }
}