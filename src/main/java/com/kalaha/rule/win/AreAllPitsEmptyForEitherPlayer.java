package com.kalaha.rule.win;

import com.kalaha.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rule implementation to check if game is finished and establish winner.
 */
@Slf4j
public class AreAllPitsEmptyForEitherPlayer implements WinRule {

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(GameData gameData) {

        Map<Player, Integer> playersPitStonesCount = new HashMap<>();
        Map<Player, Integer> playersKalahaStonesCount = new HashMap<>();

        gameData.getPits().forEach(pit -> {

            Player player = pit.getPlayer();

            if (pit instanceof Kalaha) {
                Integer count = playersKalahaStonesCount.getOrDefault(player, 0);
                playersKalahaStonesCount.put(player, count + pit.getStones());
                return;
            }
            Integer count = playersPitStonesCount.getOrDefault(player, 0);
            playersPitStonesCount.put(player, count + pit.getStones());
        });


        Player playerWithNoStones = Collections.min(playersPitStonesCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        if (playersPitStonesCount.get(playerWithNoStones) == 0) {

            Player firstPlayer = gameData.getFirstPlayer();
            Player secondPlayer = gameData.getSecondPlayer();

            Player playerWithStones = firstPlayer.equals(playerWithNoStones) ? secondPlayer : firstPlayer;
            playersKalahaStonesCount.put(playerWithStones, playersKalahaStonesCount.get(playerWithStones) + playersPitStonesCount.get(playerWithStones));

            Player winner = Collections.max(playersKalahaStonesCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            gameData.getGameInfo().setWinner(winner);

            Player loser = firstPlayer.equals(winner) ? secondPlayer : firstPlayer;

            // Sort out loser's pits
            List<Pit> pits = gameData.getPits();
            for (Pit pit : pits) {
                if (pit.getPlayer().equals(loser)) {
                    if (pit instanceof Kalaha) {
                        pit.setStones(playersKalahaStonesCount.get(loser));
                        continue;
                    }
                    pit.setStones(0);
                }
            }

            log.info("Game completed - Winner: {}", winner);
            return;
        }
        log.info("Game ongoing");
    }
}