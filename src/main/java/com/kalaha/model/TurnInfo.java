package com.kalaha.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.EnumMap;

/**
 * Represents game states like turns and wins.
 */
@NoArgsConstructor
public class TurnInfo {

    @Getter
    @Setter
    private TurnStatus turnStatus;

    public enum TurnStatus {
        TURN_P1,
        TURN_P2
    }

    private EnumMap<TurnStatus, Player> turnStatusToPlayerMap;

    public TurnInfo(Player firstPlayer, Player secondPlayer) {
        turnStatus = TurnStatus.TURN_P1;
        turnStatusToPlayerMap = new EnumMap<TurnStatus, Player>(TurnStatus.class);
        turnStatusToPlayerMap.put(TurnStatus.TURN_P1,firstPlayer);
        turnStatusToPlayerMap.put(TurnStatus.TURN_P2, secondPlayer);
    }

    public void flipTurn(){
        if(turnStatus == TurnStatus.TURN_P1){
            turnStatus = TurnStatus.TURN_P2;
        }else{
            turnStatus = TurnStatus.TURN_P1;
        }
    }

    public Player whoseTurn(){
        return turnStatusToPlayerMap.get(turnStatus);
    }
}