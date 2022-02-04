package com.kalaha.client.service;

import com.kalaha.model.GameData;
import com.kalaha.model.PlayData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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


    /**
     * Fetches data that represents the game.
     * TODO: Replace GameData with a POJO to break dependency between client and server.
     * Remove system outs, add proper logging mechanism. Look into error handling.
     *
     * @return The game data.
     */
    public GameData getGameData() {

        System.out.println("Fetching all game data through REST..");

        // Fetch from 3rd party API; configure fetch
        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().get()
                .uri("http://localhost:" + serverPort + "/game");

        GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();
        return gameData;
    }


    /**
     * Sends data that represents a turn in the game. Then retrieves the updated game data.
     * TODO: Replace GameData with a POJO to break dependency between client and server.
     * Remove system outs, add proper logging mechanism. Look into error handling.
     *
     * @param playData
     * The data represents a turn.
     * @return The game data.
     */
    public GameData sendPlayData(PlayData playData) {

        System.out.println("sending play data...");

        // Fetch from 3rd party API; configure fetch
        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().put()
                .uri("http://localhost:" + serverPort + "/game").body(Mono.just(playData), PlayData.class);

        final GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();

        return gameData;
    }
}