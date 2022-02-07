package com.kalaha.client.service;

import com.kalaha.client.dto.GameConfig;
import com.kalaha.client.dto.GameData;
import com.kalaha.client.dto.PlayData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The service that allows client to consume rest apis.
 * {@link #createGame(GameConfig)}, {@link #getGameData()} and {@link #sendPlayData(PlayData)} uses a DTO class to map the JSON results from API.
 * It fetches all available results immediately.
 */
@Service
public class RestClientService implements Serializable {

    /**
     * The port changes depending on where we deploy the app
     */
    @Value("${server.port}")
    private String serverPort;

    /**
     * Initiates a request to the relevant REST API to create a game.
     *
     * @param gameConfig the game configuration parameters.
     * @return the representation of the created game.
     */
    public GameData createGame(GameConfig gameConfig) {

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().post()
                .uri("http://localhost:" + serverPort + "/game").body(Mono.just(gameConfig), GameConfig.class);

        final GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();
        return gameData;
    }

    /**
     * Initiates a request to the relevant REST API to retrieve game data.
     *
     * @return the representation of the created game.
     */
    public GameData getGameData() {

        System.out.println("Fetching all game data through REST..");

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().get()
                .uri("http://localhost:" + serverPort + "/game");

        GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();
        return gameData;
    }


    /**
     * Initiates a request to the relevant REST API to send play data.
     *
     * @param playData The data that represents a turn.
     * @return the representation of the created game.
     */
    public GameData sendPlayData(PlayData playData) {

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().put()
                .uri("http://localhost:" + serverPort + "/game").body(Mono.just(playData), PlayData.class);

        final GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();

        return gameData;
    }
}