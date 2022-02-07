package com.kalaha.client.service;

import com.kalaha.client.dto.GameConfig;
import com.kalaha.client.dto.GameData;
import com.kalaha.client.dto.PlayData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The service that allows client to consume rest apis.
 * {@link #createGame(GameConfig)}, {@link #getGameData()} and {@link #sendPlayData(PlayData)} uses a DTO class to map the JSON results from API.
 * It fetches all available results immediately.
 */
@Service
public class RestClientService {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(RestClientService.class);

    /**
     * The port changes depending on where we deploy the app
     */
    @Value("${server.port}")
    private String serverPort;

    @Value("${server.host}")
    private String serverHost;

    @Value("${server.kalaha.endpoint}")
    private String endPoint;

    /**
     * Initiates a request to the relevant REST API to create a game.
     *
     * @param gameConfig the game configuration parameters.
     * @return the representation of the created game.
     */
    public GameData createGame(GameConfig gameConfig) {

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().post()
                .uri(serverHost + serverPort + endPoint).body(Mono.just(gameConfig), GameConfig.class);

        final GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();

        logger.debug("Game created: {}", gameData);
        return gameData;
    }

    /**
     * Initiates a request to the relevant REST API to retrieve game data.
     *
     * @return the representation of the created game.
     */
    public GameData getGameData() {

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().get()
                .uri(serverHost + serverPort + endPoint);

        GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();
        logger.debug("Game retrieved: {}", gameData);
        return gameData;
    }


    /**
     * Initiates a request to the relevant REST API to send play data.
     *
     * @param playData The data that represents a turn.
     * @return the representation of the created game.
     */
    public GameData sendPlayData(PlayData playData) {

        logger.debug("Play data sent: {}", playData);

        final WebClient.RequestHeadersSpec<?> spec = WebClient.create().put()
                .uri(serverHost + serverPort + endPoint).body(Mono.just(playData), PlayData.class);

        final GameData gameData = spec.retrieve().toEntity(GameData.class).block().getBody();
        logger.debug("Game retrieved: {}", gameData);
        return gameData;
    }
}