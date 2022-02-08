package com.kalaha.application;

import com.kalaha.client.service.RestClientService;
import com.kalaha.controller.RestController;
import com.kalaha.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class GameApplicationTests {

	@Autowired
	private RestController restController;

	@Autowired
	private GameService gameService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RestClientService restClientService;

	@Test
	void contextLoads() {

		assertThat(restController).isNotNull();
		assertThat(gameService).isNotNull();
		assertThat(restTemplate).isNotNull();
		assertThat(restClientService).isNotNull();
	}
}