package com.kalaha.client.service;

import com.kalaha.client.dto.GameConfig;
import com.kalaha.client.dto.GameData;
import com.kalaha.client.dto.PlayData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The service that allows client to consume rest apis.
 * {@link #getGameData()} and {@link #sendPlayData(PlayData)} uses a DTO class to map the JSON results from API.
 * It fetches all available results immediately.
 * <p>
 */
@Service
public class RestClientService implements Serializable {

    /**
     * The port changes depending on where we deploy the app
     */
    @Value("${server.port}")
    private String serverPort;


    public GameData createGame(GameConfig gameConfig) {

        System.out.println("Creating game data through REST..");

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().post()
                .uri("http://localhost:" + serverPort + "/game").body(Mono.just(gameConfig), GameConfig.class);

        final GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();
        return gameData;
    }

    /**
     * Fetches data that represents the game.
     * Remove system outs, add proper logging mechanism. Look into error handling.
     *
     * @return The game data.
     */
    public GameData getGameData() {

        System.out.println("Fetching all game data through REST..");

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().get()
                .uri("http://localhost:" + serverPort + "/game");

        GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();
        return gameData;
    }


    /**
     * Sends data that represents a turn in the game. Then retrieves the updated game data.
     * Remove system outs, add proper logging mechanism. Look into error handling.
     *
     * @param playData
     * The data represents a turn.
     * @return The game data.
     */
    public GameData sendPlayData(PlayData playData) {

        System.out.println("sending play data...");

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().put()
                .uri("http://localhost:" + serverPort + "/game").body(Mono.just(playData), PlayData.class);

        final GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();

        return gameData;
    }
}