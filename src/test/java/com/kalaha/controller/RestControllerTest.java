package com.kalaha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalaha.application.GameApplication;
import com.kalaha.model.GameConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GameApplication.class)
@AutoConfigureMockMvc
class RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestController restController;

    @Test
    public void contextLoads() {
        assertThat(restController).isNotNull();
    }

    @Test
    void newGame() throws Exception {
        String expectedJson = "{\"pits\":[{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":6},{\"player\":{\"id\":0,\"name\":\"Gokhan\"},\"stones\":0},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":6},{\"player\":{\"id\":1,\"name\":\"Mathilde\"},\"stones\":0}],\"turnInfo\":{\"toPlay\":{\"id\":0,\"name\":\"Gokhan\"},\"firstPlayer\":{\"id\":0,\"name\":\"Gokhan\"},\"secondPlayer\":{\"id\":1,\"name\":\"Mathilde\"}},\"violationInfo\":[],\"gameInfo\":{\"gameStatus\":\"ONGOING\",\"winner\":null},\"playData\":{\"selectedPit\":0,\"player\":null},\"firstPlayer\":{\"id\":0,\"name\":\"Gokhan\"},\"secondPlayer\":{\"id\":1,\"name\":\"Mathilde\"},\"currentIndex\":0}";
        GameConfig gameConfig = new GameConfig("Gokhan", "Mathilde", 6, 6, Collections.emptyList());
        this.mockMvc.perform(post("/game").content(new ObjectMapper().writeValueAsString(gameConfig))
                        .contentType(MediaType.APPLICATION_JSON)).
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