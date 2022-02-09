package com.kalaha.client.service;

import com.kalaha.client.dto.GameConfig;
import com.kalaha.client.dto.GameData;
import com.kalaha.client.dto.PlayData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * The service that allows client to consume rest apis.
 * {@link #createGame(GameConfig)}, {@link #getGameData(long)} and {@link #sendPlayData(PlayData)} uses a DTO class to map the JSON results from API.
 * It fetches all available results immediately.
 */
@Slf4j
@Service
public class RestClientService {

    /**
     * The port changes depending on where we deploy the app
     */
    @Value("${server.port}")
    private String serverPort;

    /**
     * The host where we deploy the app
     */
    @Value("${server.host}")
    private String serverHost;

    /**
     * The game endpoint
     */
    @Value("${server.kalaha.endpoint}")
    private String endPoint;

    /**
     * The rest template to be used.
     */
    private final RestTemplate restTemplate;

    /**
     * Constructs a rest client service.
     *
     * @param restTemplate the rest template to be used.
     */
    @Autowired
    public RestClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * Initiates a request to the relevant REST API to create a game.
     *
     * @param gameConfig the game configuration parameters.
     * @return the location of the created game.
     */
    public URI createGame(GameConfig gameConfig) {

        HttpEntity<GameConfig> request = new HttpEntity<>(gameConfig);
        URI locationHeader = restTemplate.postForLocation(serverHost + serverPort + endPoint, request);
        log.debug("Game created: {}", locationHeader);
        return locationHeader;
    }

    /**
     * Initiates a request to the relevant REST API to retrieve game data.
     * @param id the identifier of the game to be retrieved.
     * @return the representation of the created game.
     */
    public GameData getGameData(long id) {

        GameData gameData = restTemplate.getForObject(serverHost + serverPort + endPoint + "/" + id, GameData.class);
        log.debug("Game retrieved: {}", gameData);
        return gameData;
    }

    /**
     * Initiates a request to the relevant REST API to send play data.
     *
     * @param playData The data that represents a turn.
     * @return the representation of the created game.
     */
    public GameData sendPlayData(PlayData playData) {

        log.debug("Play data sent: {}", playData);
        HttpEntity<PlayData> requestUpdate = new HttpEntity<>(playData);
        ResponseEntity<GameData> responseEntity = restTemplate.exchange(serverHost + serverPort + endPoint, HttpMethod.PUT, requestUpdate, GameData.class);
        GameData gameData = responseEntity.getBody();
        log.debug("Game retrieved: {}", gameData);
        return gameData;
    }

}