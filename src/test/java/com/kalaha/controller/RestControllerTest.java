package com.kalaha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalaha.application.GameApplication;
import com.kalaha.client.dto.PlayData;
import com.kalaha.client.dto.Player;
import com.kalaha.model.GameConfig;
import com.kalaha.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = GameApplication.class)
@AutoConfigureMockMvc
class RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestController restController;

    private static GameConfig gameConfig;

    @Autowired
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        gameConfig = new GameConfig("Gokhan", "Mathilde",
                6, 6, Collections.emptyList());
    }

    @Test
    public void contextLoads() {
        assertThat(restController).isNotNull();
    }

    @Test
    void verifyGameCreation() throws Exception {
        this.mockMvc.perform(post("/game").
                        content(new ObjectMapper().writeValueAsString(gameConfig)).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).
                andExpect(content().string("")).
                andExpect(header().string("location","http://localhost/game/1"));
    }

    @Test
    void verifyRetrievalOfGame() throws Exception {

        gameService.newGame(gameConfig);

        String expectedJson = "{\"id\":1,\"pits\":[{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":0},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":0}],\"turnInfo\":{\"toPlay\":{\"id\":0,\"name\":\"Gokhan\"}},\"violationInfo\":[],\"gameInfo\":{\"gameStatus\":\"ONGOING\",\"winner\":null},\"playData\":{\"selectedPit\":0,\"player\":null},\"firstPlayer\":{\"id\":0,\"name\":\"Gokhan\"},\"secondPlayer\":{\"id\":1,\"name\":\"Mathilde\"},\"currentIndex\":0}";
        this.mockMvc.perform(get("/game/1").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string(expectedJson));
    }

    @Test
    void verifyPlayDataReflectedInGame() throws Exception {

        gameService.newGame(gameConfig);

        PlayData playData = new PlayData(0, new Player(0, "Gokhan"));
        String expectedJson = "{\"id\":1,\"pits\":[{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":0},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":7},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":7},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":7},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":7},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":7},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":1},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":0}],\"turnInfo\":{\"toPlay\":{\"id\":0,\"name\":\"Gokhan\"}},\"violationInfo\":[],\"gameInfo\":{\"gameStatus\":\"ONGOING\",\"winner\":null},\"playData\":{\"selectedPit\":0,\"player\":{\"id\":0,\"name\":\"Gokhan\"}},\"firstPlayer\":{\"id\":0,\"name\":\"Gokhan\"},\"secondPlayer\":{\"id\":1,\"name\":\"Mathilde\"},\"currentIndex\":6}";
        this.mockMvc.perform(put("/game").
                        content(new ObjectMapper().writeValueAsString(playData)).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string(expectedJson));
    }
}