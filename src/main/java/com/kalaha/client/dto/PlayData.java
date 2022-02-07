package com.kalaha.client.dto;

import lombok.*;

/**
 * Client side POJO that holds a play data, e.g. selected pit and player who selected it.
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
