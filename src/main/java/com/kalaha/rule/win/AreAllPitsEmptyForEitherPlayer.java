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

        gameData.getPits().stream().forEach(pit -> {

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

            playersKalahaCount.entrySet().forEach(entry -> entry.setValue(entry.getValue() + playersKalahaCount.get(entry.getKey())));
            Player player = Collections.max(playersKalahaCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            gameData.getGameInfo().establishWinner(player);
            //TODO: Set kalaha stone number for the player.
        }


    }
}
