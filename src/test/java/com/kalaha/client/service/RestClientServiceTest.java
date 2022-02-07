package com.kalaha.client.service;

import com.kalaha.client.dto.GameConfig;
import com.kalaha.client.dto.GameData;
import com.kalaha.client.dto.PlayData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RestClientServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClientService restClientService;

    @Test
    public void whenCallingCreateGame_thenClientReceivesAGame() {

        GameConfig gameConfig = new GameConfig();
        HttpEntity<GameConfig> request = new HttpEntity<>(gameConfig);
        GameData gameData = new GameData();
        Mockito.when(restTemplate.postForObject("nullnullnull",request, GameData.class)).thenReturn(gameData);

        GameData returnedGameData = restClientService.createGame(gameConfig);
        Assertions.assertEquals(gameData, returnedGameData);
    }

    @Test
    public void whenCallingGetGame_thenClientReceivesGame() {

        GameData gameData = new GameData();
        Mockito.when(restTemplate.getForObject("nullnullnull", GameData.class)).thenReturn(gameData);

        GameData returnedGameData = restClientService.getGameData();
        Assertions.assertEquals(gameData, returnedGameData);
    }

    @Test
    public void whenCallingSendPlayData_thenClientReceivesGame() {

        GameData gameData = new GameData();
        ResponseEntity<GameData> responseEntity = new ResponseEntity<>(gameData,HttpStatus.OK);
        PlayData playData = new PlayData();
        HttpEntity<PlayData> request = new HttpEntity<>(playData);
        Mockito.when(restTemplate.exchange("nullnullnull", HttpMethod.PUT, request, GameData.class)).thenReturn(responseEntity);

        GameData returnedGameData = restClientService.sendPlayData(playData);
        Assertions.assertEquals(gameData, returnedGameData);
    }

}