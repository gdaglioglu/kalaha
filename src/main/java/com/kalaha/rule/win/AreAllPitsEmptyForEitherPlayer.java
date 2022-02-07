package com.kalaha.rule.win;

import com.kalaha.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Rule implementation to check if game is finished and establish winner.
 */
public class AreAllPitsEmptyForEitherPlayer implements WinRule {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(AreAllPitsEmptyForEitherPlayer.class);

    /**
     * {@inheritDoc}
     */
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
            gameData.getGameInfo().setWinner(winner);

            // Set lost user's kalaha stones in game state
            Pit lostUsersKalaha = gameData.getPits().stream().filter(pit -> pit instanceof Kalaha && pit.getPlayer().equals(playerWithStonesLeft)).findFirst().get();
            gameData.getPits().stream().filter(pit -> !(pit instanceof Kalaha) && pit.getPlayer().equals(playerWithStonesLeft)).forEach(pit -> pit.setStones(0));
            lostUsersKalaha.setStones(lostUsersKalaha.getStones() + playersKalahaCount.get(playerWithStonesLeft));
            //Game winner must be set in here!!

            logger.info("Game completed - Winner: {}", winner);
        }

        logger.info("Game ongoing");
    }
}
