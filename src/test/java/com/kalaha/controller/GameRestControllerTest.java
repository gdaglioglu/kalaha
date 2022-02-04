package com.kalaha.controller;

import com.kalaha.application.GameApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GameApplication.class)
@AutoConfigureMockMvc
class GameRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRestController gameRestController;

    @Test
    public void contextLoads() {
        assertThat(gameRestController).isNotNull();
    }

    @Test
    void newGame() throws Exception {
        String expectedJson = "{\"pits\":[{\"player\":{\"id\":0,\"name\":\"Player 1\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Player 1\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Player 1\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Player 1\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Player 1\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Player 1\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Player 1\"},\"stones\":0},{\"player\":{\"id\":1,\"name\":\"Player 2\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Player 2\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Player 2\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Player 2\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Player 2\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Player 2\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Player 2\"},\"stones\":0}],\"gameState\":\"TURN_P1\",\"currentIndex\":0}";

        this.mockMvc.perform(post("/game")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().string(expectedJson));
    }

    @Test
    void getGame() {
    }

    @Test
    void play() {
    }
}