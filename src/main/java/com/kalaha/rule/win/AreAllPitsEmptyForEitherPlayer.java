package com.kalaha.rule.win;

import com.kalaha.model.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AreAllPitsEmptyForEitherPlayer implements WinRule {

    @Override
    public void run(GameData gameData) {

        Map<Player, Integer> playersPitCount = new HashMap<>();
        Map<Player, Integer> playersKalahaCount = new HashMap<>();

        gameData.getPits().forEach(pit -> {

            Player player = pit.getPlayer();

            if (pit instanceof Kalaha) {
                Integer count = playersKalahaCount.getOrDefault(player, 0);
                playersKalahaCount.put(player, count + pit.getStones());
                return;
            }
            Integer count = playersPitCount.getOrDefault(player, 0);
            playersPitCount.put(player, count + pit.getStones());
        });

        if (playersPitCount.containsValue(0)) {

            //TODO: this logic to be simplified and performant.

            Player playerWithStonesLeft = Collections.max(playersPitCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            playersKalahaCount.put(playerWithStonesLeft, playersPitCount.get(playerWithStonesLeft));
            Player winner = Collections.max(playersKalahaCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            gameData.getGameInfo().establishWinner(winner);

            // Set lost user's kalaha stones in game state
            Pit lostUsersKalaha = gameData.getPits().stream().filter(pit -> pit instanceof Kalaha && pit.getPlayer().equals(playerWithStonesLeft)).findFirst().get();
            gameData.getPits().stream().filter(pit -> !(pit instanceof Kalaha) && pit.getPlayer().equals(playerWithStonesLeft)).forEach(pit -> pit.setStones(0));
            lostUsersKalaha.setStones(lostUsersKalaha.getStones() + playersKalahaCount.get(playerWithStonesLeft));
        }
    }
}
